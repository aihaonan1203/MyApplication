package com.zcf.bananavideohannan.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.squareup.picasso.Picasso;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.view.MyUserActionStandard;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by yanjin on 2017/8/5.
 * 视频播放
 */
@ContentView(R.layout.activity_play_video)
public class PlayVideoActivity extends MyBaseActivity {
    private Context mContext;
    private String TAG = "PlayVideoActivity";

    /**
     * 视频地址
     */
    public static String EXTRA_FILEURL = "extra_fileUrl";
    @ViewInject(R.id.jc_video)
    private JCVideoPlayerStandard jc_video;
    /**
     * 视频播放
     */
    @ViewInject(R.id.space_play_video)
    private SurfaceView surview;


    // 视频播放地址
    private String video_url;

    // 视频本地缓存地址
    private String video_path;

    /**
     * 是否播放
     */
    private boolean isPlay = false;
    /**
     * 播放器
     */
    private MediaPlayer mPlayer;

    /**
     * 播放界面画布回调接口
     */
    private MyCallBack callBack;

    /**
     * 暂停位置
     */
    private int pausePosition = 0;

    /**
     * 视频下载成功
     */
    public static final int HANDLER_LOAD_VIDEO_SUCCESS = 0;
    /**
     * 播放器报错
     */
    public static final int HANDLER_SURFACE_FAILED = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_LOAD_VIDEO_SUCCESS:
                    // 视频下载成功，开始播放
                    if (msg.arg1 == 0) {
                        Bundle data = msg.getData();
                        if (data != null) {
                            if (data.containsKey("filePath")) {
                                video_path = data.getString("filePath");
                                if (!TextUtils.isEmpty(video_path)) {
                                    play();
                                }
                            }
                        }
                    }
                    if (msg.arg1 == -1) {
                        ToastUtil.toastShort(mContext, "下载失败");
                        finish();
                    }
                    break;
                case HANDLER_SURFACE_FAILED:
                    ToastUtil.toastShort(mContext, "地址不正确");
                    finish();
                    break;
            }
        }
    };
    public static final String EXTRA_VIDEO_PATH = "video_patjh";
    public static final String EXTRA_VIDEO_IMAGE = "video_pimg";
    public static final String EXTRA_VIDEO_NAME = "video_name";
    private String iconUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(EXTRA_VIDEO_PATH)) {
                video_path = extras.getString(EXTRA_VIDEO_PATH);
            }
            if (extras.containsKey(EXTRA_VIDEO_IMAGE)) {
                iconUrl = extras.getString(EXTRA_VIDEO_IMAGE);
            }
            if (extras.containsKey(EXTRA_VIDEO_NAME)) {
                title = extras.getString(EXTRA_VIDEO_NAME);
            }
//            play();
            initVideoPlayer();
        }


        setListeners();
    }

    private void initVideoPlayer() {

        jc_video.setUp(video_path, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        Picasso.with(this).load(iconUrl).into(jc_video.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());

    }

    String VIDEO_DIR = Environment.getExternalStorageDirectory().getPath() + "/scssc/picture/";

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    private void setListeners() {
        // 视频点击关闭
        surview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePayer();
                finish();
            }
        });
    }

    /**
     * 播放本地视频
     */
    private void play() {
        try {
            isPlay = true;
            if (!TextUtils.isEmpty(video_path)) {
                LOG.i(TAG, "开始播放视频~~");
                surview.setVisibility(View.VISIBLE);
                SurfaceHolder surholder = surview.getHolder();
                callBack = new MyCallBack();
                surholder.addCallback(callBack);
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopPlay();
        }
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if (isPlay) {
                    File file = new File(video_path);
                    if (file.exists() && file.isFile()) {
                        LOG.i(TAG, "开始播放视频~~设置播放参数");
                        mPlayer = MediaPlayer.create(mContext, Uri.parse(video_path), holder);

                        if (mPlayer != null) {
                            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                @Override
                                public boolean onError(MediaPlayer mp, int what, int extra) {
                                    LOG.i(TAG, "播放器报错..." + "  error:" + what + "  extra:" + extra);
                                    isPlay = false;
                                    surview.setVisibility(View.GONE);
                                    return false;
                                }
                            });
                            mPlayer.stop();
                            mPlayer.prepare();
                            if (pausePosition > 0) {
                                mPlayer.seekTo(pausePosition);
                            }
                            mPlayer.setLooping(true);//循环
                            mPlayer.start();
                        } else {
                            handler.sendEmptyMessage(HANDLER_SURFACE_FAILED);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            LOG.i(TAG, "视频播放器销毁了");
            isPlay = false;
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer.release();
            }
            mPlayer = null;
        }

    }

    /**
     * 停止播放
     */
    private void stopPlay() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        surview.setVisibility(View.GONE);

    }

    /**
     * 释放播放器资源
     */
    private void releasePayer() {
        try {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            isPlay = false;
            surview.setVisibility(View.GONE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
