package com.bz.poverty;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.framework.activity.BaseActivity;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.widget.VideoView;

/**
 * Created by chenxi.cui on 2017/10/23.
 */

public class PlayerActivity extends BaseActivity implements View.OnTouchListener {

    VideoView videoView;
    //        String playUrl = "rtmp://rtmp9.public.topvdn.cn/live/537009139_134283008_1473738972_0654d5f3c24a90a8a183a3d86cdf527c";
//    String playUrl = "rtmp://7ae2b574.server.topvdn.com:1935/live/537025757_134283008_1508576809_6e52fc80d87a4c386d95cdf5a7954df9";
    String playUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        CameraDetailResult.CameraBean item = (CameraDetailResult.CameraBean) myBundle.getSerializable("item");

        String rtmp = myBundle.getString("rtmp");
        progressBar = findViewById(R.id.progress_bar);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setOnTouchListener(this);
        videoView.playVideo(rtmp);
        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.v("PlayerActivity", ":" + iMediaPlayer.getCurrentPosition());
                progressBar.setVisibility(View.GONE);
            }
        });
        //videoView.playLyyRTMPVideo("");
        //videoView.startLyyAudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) videoView.stopPlayback(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.videoView) {
            //播放器自带缩放功能
            v.onTouchEvent(event);
            return true;
        }
        return false;
    }
}
