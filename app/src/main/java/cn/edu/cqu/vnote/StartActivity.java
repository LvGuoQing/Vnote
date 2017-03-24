package cn.edu.cqu.vnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import cn.edu.cqu.vnote.Activity.BucketHomeFragmentActivity;

/**
 * Created by lenovo on 2017/3/22.
 */

public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.startup, null);
        setContentView(view);

        //the start page will play in grdale change
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });


    }

    private void redirectTo(){
        Intent intent = new Intent(this,BucketHomeFragmentActivity.class);
        startActivity(intent);
        finish();
    }
}
