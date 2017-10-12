package com.bz.poverty;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.framework.activity.BaseFragment;
import com.haolb.client.utils.SecureUtil;

/**
 * Created by chenxi.cui on 2017/9/30.
 */
public class HomeFragment extends BaseFragment {
    private VideoView videoView;
    private MediaController mc;
    private LinearLayout llRoot;
    private LinearLayout.LayoutParams layoutParams;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateViewWithTitleBar(inflater, container, R.layout.fragemtn_home);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        llRoot = (LinearLayout) getView().findViewById(R.id.ll_root);
//        videoView = (VideoView) getView().findViewById(R.id.videoView);
         layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT);
        setVideo();
    }

    private void setVideo() {
        if (llRoot.getChildCount() > 0) {
            return;
        }
        videoView = new VideoView(getContext());
        llRoot.addView(videoView, layoutParams);
        mc = new MediaController(getContext());
//        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.b));
        videoView.setMediaController(mc);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
            }
        });
        videoView.setZOrderMediaOverlay(true);
    }


    @Override
    protected void onHide() {
        super.onHide();
        if (videoView != null) {
            videoView.pause();
        }
        llRoot.removeAllViews();
        videoView = null;
    }

    @Override
    protected void onShow() {
        super.onShow();
        setVideo();
        if (videoView != null) {
            videoView.start();
        }
    }
}
