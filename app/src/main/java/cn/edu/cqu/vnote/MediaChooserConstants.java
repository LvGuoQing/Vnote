package cn.edu.cqu.vnote;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import java.io.File;

/**
 * Created by lenovo on 2017/3/23.
 */
public class MediaChooserConstants {
	public static String folderName = "Vnote";
	public static int MAX_MEDIA_LIMIT = 100;
	public static int SELECTED_MEDIA_COUNT  = 0;
	public static boolean showCameraVideo = true;
	public static boolean showVideo 	  = true;
	public static boolean showImage 	  = true;
	public static int SELECTED_IMAGE_SIZE_IN_MB = 20;
	public static int SELECTED_VIDEO_SIZE_IN_MB = 20;
	public static final int BUCKET_SELECT_IMAGE_CODE = 1000;
	public static final int BUCKET_SELECT_VIDEO_CODE = 2000;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	public static long ChekcMediaFileSize(File mediaFile, boolean isVideo){

		//Get length of file in bytes
		long fileSizeInBytes = mediaFile.length();
		//Convert the bytes to Kb
		long fileSizeInKB = fileSizeInBytes / 1024;
		long fileSizeInMB = fileSizeInKB / 1024;
		int requireSize = 0;
		if(isVideo){
			requireSize = MediaChooserConstants.SELECTED_VIDEO_SIZE_IN_MB;
		}else{
			requireSize = MediaChooserConstants.SELECTED_IMAGE_SIZE_IN_MB;
		}
		if (fileSizeInMB <= requireSize) {
			return 0;
		}
		return fileSizeInMB;
	}


	public static AlertDialog.Builder getDialog(Context context){
		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setCancelable(false);
		dialog.setTitle(context.getString(R.string.please_wait_text));
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialog.setView(layoutInflater.inflate(R.layout.view_loading_media_chooser, null));
		return dialog;
	}

}
