package cn.edu.cqu.vnote;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.edu.cqu.vnote.adapter.BucketGridAdapter;
import cn.edu.cqu.vnote.adapter.GridViewAdapter;
import cn.edu.cqu.vnote.fragment.BucketVideoFragment;
import cn.edu.cqu.vnote.fragment.VideoFragment;


/**
 * Created by lenovo on 2017/3/23.
 */
public class GalleryCache {
	private LruCache<String, Bitmap> mBitmapCache;
	private ArrayList<String> mCurrentTasks;
	private int mMaxWidth;

	public GalleryCache(int size, int maxWidth, int maxHeight) {
		mMaxWidth = maxWidth;

		mBitmapCache = new LruCache<String, Bitmap>(size) {
			@Override
			protected int sizeOf(String key, Bitmap b) {
				// Assuming that one pixel contains four bytes.
				return b.getHeight() * b.getWidth() * 4;
			}
		};

		mCurrentTasks = new ArrayList<String>();
	}

	private void addBitmapToCache(String key, Bitmap bitmap) {
		if (getBitmapFromCache(key) == null) {
			mBitmapCache.put(key, bitmap);
		}
	}

	private Bitmap getBitmapFromCache(String key) {
		return mBitmapCache.get(key);
	}

	public void loadBitmap(Fragment mainActivity, String imageKey,
			ImageView imageView, boolean isScrolling) {
		final Bitmap bitmap = getBitmapFromCache(imageKey);
		

		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.ic_loading);
			//			imageView.setImageResource(R.drawable.transprent_drawable);
			if (!isScrolling && !mCurrentTasks.contains(imageKey)) {
				if(mainActivity instanceof VideoFragment){
					BitmapLoaderTask task = new BitmapLoaderTask(imageKey, ((VideoFragment)mainActivity).getAdapter());
					task.execute();

				}else if(mainActivity instanceof BucketVideoFragment){
					BitmapLoaderTask task = new BitmapLoaderTask(imageKey, ((BucketVideoFragment)mainActivity).getAdapter());
					task.execute();
				}
			}
		}
	}

	private class BitmapLoaderTask extends AsyncTask<Void, Void, Bitmap> {
		private GridViewAdapter mAdapter;
		private BucketGridAdapter mBucketGridAdapter;
		private String mImageKey;

		public BitmapLoaderTask(String imageKey, GridViewAdapter adapter) {
			mAdapter = adapter;
			mImageKey = imageKey;
		}

		public BitmapLoaderTask(String imageKey, BucketGridAdapter adapter) {
			mBucketGridAdapter = adapter;
			mImageKey = imageKey;
		}

		@Override
		protected void onPreExecute() {
			mCurrentTasks.add(mImageKey);
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap bitmap = null;
			try {
				bitmap = ThumbnailUtils.createVideoThumbnail(mImageKey, Thumbnails.FULL_SCREEN_KIND);

				if (bitmap != null) {
					bitmap = Bitmap.createScaledBitmap(bitmap, mMaxWidth, mMaxWidth, false);
					addBitmapToCache(mImageKey, bitmap);
					return bitmap;
				}
				return null;
			} catch (Exception e) {
				if (e != null) {
					e.printStackTrace();
				}
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap param) {
			mCurrentTasks.remove(mImageKey);
			if (param != null) {
				if(mAdapter != null){
					mAdapter.notifyDataSetChanged();
				}else{
					mBucketGridAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	public void clear() {
		mBitmapCache.evictAll();
	}



}
