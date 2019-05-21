package com.zcf.bananavideohannan;

import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.view.MyUserActionStandard;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

@ContentView(R.layout.activity_test_video)
public class TestActivity extends MyBaseActivity {

    @ViewInject(R.id.jc_video)
    private JCVideoPlayerStandard jc_video;
    public static final String EXTRA_VIDEROURL = "extra_videUrl";
    public static final String EXTRA_ICONURL = "extra_iconUrl";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_VIDEO_ID = "extra_video_id";
    private String videoUrl; // 视频播放地址
    private String iconUrl; // 视频缩略图
    private String title; // 标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_VIDEROURL) && bundle.getString(EXTRA_VIDEROURL) != null) {
                videoUrl = bundle.getString(EXTRA_VIDEROURL).toString();
            }
            if (bundle.containsKey(EXTRA_ICONURL) && bundle.getString(EXTRA_ICONURL) != null) {
                iconUrl = bundle.getString(EXTRA_ICONURL).toString();
            }
            if (bundle.containsKey(EXTRA_TITLE) && bundle.getString(EXTRA_TITLE) != null) {
                title = bundle.getString(EXTRA_TITLE).toString();
            }
        }

        initVideoPlayer();
    }

    private void initVideoPlayer() {
        jc_video.setUp(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        Picasso.with(mContext).load(iconUrl).into(jc_video.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        jc_video.startVideo();
    }

    @Override
    public void onPause() {
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }
}
