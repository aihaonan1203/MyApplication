package com.zcf.bananavideohannan.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.log.LogFile;
import com.gaotai.baselib.util.AndroidUtil;
import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.util.FileUtils;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.squareup.picasso.Picasso;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.login.LoginActivity;
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.adapter.RecyFilmGengralAdapter;
import com.zcf.bananavideohannan.adapter.RecyPingLunAdapter;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.CommentlistBean;
import com.zcf.bananavideohannan.bean.LabelBean;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.ReboBean;
import com.zcf.bananavideohannan.bean.videodetail.VideoContentBean;
import com.zcf.bananavideohannan.bean.videodetail.VideoLikeResultBean;
import com.zcf.bananavideohannan.bll.DownBll;
import com.zcf.bananavideohannan.dbmodel.FilmPinglunDBModel;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;
import com.zcf.bananavideohannan.domain.AdDomain;
import com.zcf.bananavideohannan.domain.BqSearchDomain;
import com.zcf.bananavideohannan.network.DownLoadFileUtils;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.util.OrientationListener;
import com.zcf.bananavideohannan.util.SPUtils;
import com.zcf.bananavideohannan.util.ToastUtils;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.MyUserActionStandard;
import com.zcf.bananavideohannan.view.SaundProgressBar;
import com.zcf.bananavideohannan.view.XCRoundRectImageView;
import com.zcf.bananavideohannan.view.dialog.DialogFilmBrief;
import com.zcf.bananavideohannan.view.dialog.DialogSendComment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.gaotai.baselib.util.AndroidUtil.dip2px;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.backPress;

/**
 * 视频播放、详情页
 */
@ContentView(R.layout.activity_play_video_detail)
public class PlayVideoDetailActivity extends MyBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.scrollView_filmdetail)
    private ScrollView scrollView_filmdetail;

    @ViewInject(R.id.jc_video)
    private JCVideoPlayerStandard jc_video;

    @ViewInject(R.id.vv_guangao)
    private VideoView iv_ad_img;

    @ViewInject(R.id.tv_less_second)
    private TextView tv_less_second;

    @ViewInject(R.id.tv_zantext)
    private TextView tv_zantext;

    @ViewInject(R.id.progressBarLoad)
    private SaundProgressBar progressBarLoad;

    @ViewInject(R.id.rg_video_type)
    private RadioGroup rg_video_type;

    @ViewInject(R.id.rb_remen)
    private RadioButton rb_remen;

    @ViewInject(R.id.rb_zuixin)
    private RadioButton rb_zuixin;

    @ViewInject(R.id.iv_like)
    private ImageView iv_like;

    @ViewInject(R.id.iv_down)
    private ImageView iv_down;

    @ViewInject(R.id.iv_share)
    private ImageView iv_share;

    @ViewInject(R.id.iv_good)
    private ImageView iv_good;

    @ViewInject(R.id.iv_bad)
    private ImageView iv_bad;

    @ViewInject(R.id.iv_top_ad)
    private XCRoundRectImageView iv_top_ad;

    @ViewInject(R.id.tv_video_name)
    private TextView tv_video_name;

    @ViewInject(R.id.tv_video_date)
    private TextView tv_video_date;

    @ViewInject(R.id.tv_playnum)
    private TextView tv_playnum;

    @ViewInject(R.id.tv_content)
    private TextView tv_content;

    @ViewInject(R.id.tv_re_comment_count)
    private TextView tv_re_comment_count;

    @ViewInject(R.id.tv_video_commentnum)
    private TextView tv_video_commentnum;

    @ViewInject(R.id.tv_send_comment)
    private TextView tv_send_comment;

//    @ViewInject(R.id.fullscreen)
//    private ImageView fullscreen;

    @ViewInject(R.id.llyt_write_feel)// 输入感想
    private LinearLayout llyt_write_feel;

    @ViewInject(R.id.llyt_write_feel1)// 输入感想
    private LinearLayout llyt_write_feel1;

    @ViewInject(R.id.lv_contents)// 评论
//    private ListViewForScrollView lv_contents;
    private RecyclerView lv_contents;

    @ViewInject(R.id.lv_your_likes)// 猜你喜欢的
//    private ListViewForScrollView lv_your_likes;
    private RecyclerView lv_your_likes;

    private SensorManager mSensorManager;
    private OrientationListener mOrientationListener;

    //    private String videoUrl; // 视频播放地址
//    private String iconUrl; // 视频缩略图
//    private String title; // 标题
//    private String video_id; //
    private List<FilmPinglunDBModel> pldata;
    private List<BqSearchDomain> films;
    //    private FilmPinglunAdapter plAdapter;
    private RecyPingLunAdapter commentAdapter;
    //    private FilmAdapter filmAdapter;
    private RecyFilmGengralAdapter recyFilmAdapter;

    public static final String EXTRA_VIDEROURL = "extra_videUrl";
    public static final String EXTRA_ICONURL = "extra_iconUrl";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_VIDEO_ID = "extra_video_id";

    @ViewInject(R.id.refreshlayout)
    private SmartRefreshLayout smartRefreshLayout;

    /**
     * 滚动的高度，显示头部按钮部件
     */
    private int scro_height;
    int height;
    /**
     * 系统标题栏高度
     */
    private int system_title_height;

    /**
     * 头部显示的高度，换算为px
     */
    private int notice_top_height;

    /**
     * 是否显示动态固定顶部区域
     */
    boolean isShowTop = false;
    private int videoY;

    private final int HADNLER_STOP_REFRESH = 1;
    private final int HADNLER_FRESH_SECOND = 2;
    private final int HADNLER_START_PLAY_VIDEO = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HADNLER_STOP_REFRESH:
                    smartRefreshLayout.finishLoadMore();
                    break;
                case HADNLER_FRESH_SECOND:
                    tv_less_second.setText(String.valueOf(current_time) + "s");
                    break;
                case HADNLER_START_PLAY_VIDEO:
                    jc_video.setVisibility(View.VISIBLE);
                    iv_ad_img.setVisibility(View.GONE);
                    tv_less_second.setVisibility(View.GONE);
                    jc_video.startVideo();
                    break;
            }
        }
    };

    private String uid;
    private CommonlyBean<CommentlistBean> comments;
    private List<BqSearchDomain> likes;
    private List<AdDomain> adlink;
    private int re_commentNum;
    private int like_status;
    private int good_status;
    private ReboBean.DataBean videoModel;
    private String token;
    private Disposable disposable;
    private String image;
    private HashMap<Object, String> label;
    private TypeBean typeBean;
    private String url;
    private boolean isok=false;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(ReboBean.DataBean model) {
        this.videoModel = model;
    }

    private LoginBean.DataBean.UserinfoBean userinfo;
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkLogin()) {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
            return;
        }
        if (userinfo.getVIP()==0){
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
            builder.setTitle("香蕉视频")
                    .setMessage("您不是VIP，不能观看视频呦...")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setCancelable(false);
            builder.show();
            return;
        }
        // 文字进度
//        progressBarLoad.setProgressIndicator(indicator);

        smartRefreshLayout = findViewById(R.id.refreshlayout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setEnableOverScrollDrag(false);

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ToastUtil.toastShort(mContext, "加载更多");
                handler.sendEmptyMessage(HADNLER_STOP_REFRESH);
            }
        });

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            if (bundle.containsKey(EXTRA_VIDEROURL) && bundle.getString(EXTRA_VIDEROURL) != null) {
//                videoUrl = bundle.getString(EXTRA_VIDEROURL).toString();
//            }
//            if (bundle.containsKey(EXTRA_ICONURL) && bundle.getString(EXTRA_ICONURL) != null) {
//                iconUrl = bundle.getString(EXTRA_ICONURL).toString();
//            }
//            if (bundle.containsKey(EXTRA_TITLE) && bundle.getString(EXTRA_TITLE) != null) {
//                title = bundle.getString(EXTRA_TITLE).toString();
//            }
//            if (bundle.containsKey(EXTRA_VIDEO_ID)) {
//                video_id = String.valueOf(bundle.getInt(EXTRA_VIDEO_ID));
//            }
//        }

        // TODO: 2018/12/3 测试数据
        initVideoPlayer();
        setListener();
        token = app.getParam(ContextProperties.REM_TOKEN).toString();
        ToastUtils.show(mContext, "加载中...");
        disposable=Network.getVideoApi().getplay_channel()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String json = responseBody.string().trim();
                        JSONObject jsonObject = new JSONObject(json);
                        final String image = jsonObject.getJSONObject("data").getString("image");
                        url = jsonObject.getJSONObject("data").getString("adlink");
                        final String  mDestFileName =image.substring(image.lastIndexOf("/"),image.lastIndexOf("."));
                        if (SPUtils.getString("videoName","").isEmpty()||!SPUtils.getString("videoName","").equals(mDestFileName)){
                            String localPath = DownLoadFileUtils.customLocalStoragePath("video");// /storage/emulated/0/image/
                            DownLoadFileUtils.downloadFile(PlayVideoDetailActivity.this, image, localPath, mDestFileName, image, new DownLoadFileUtils.MyFileCallback() {
                                @Override
                                public void onSuccess(String baseUrl) {
                                    SPUtils.saveString("videoName",mDestFileName);
                                    SPUtils.saveString("video",baseUrl);
                                    initNetData();
                                }

                                @Override
                                public void onError() {
                                    Log.e("onSuccess: ","" );
                                }
                            });
                        }else {
                            initNetData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });

//        getLikes();
    }

    private void initNetData(){
        disposable=Network.getVideoApi().getVideoNum(token,videoModel.getId())
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(ResponseBody responseBody) throws Exception {
                        JSONObject videoData = new JSONObject(responseBody.string());
                        if (videoData.getInt("code")==0){
                            return false;
                        }
                        typeBean = GsonUtil.parseJsonWithGson(videoData.getJSONObject("data").toString(),TypeBean.class);
                        DcAndroidContext.getInstance().setParam("times",typeBean.getTimes());
                        return true;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) throws Exception {
                        if (!aBoolean){
                            disposable.dispose();
                            ToastUtils.dismiss();
                            showNoNumberDialog();
                        }
                        return true;
                    }
                }).observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, Observable<CommonlyBean<CommentlistBean>>>() {
                    @Override
                    public Observable<CommonlyBean<CommentlistBean>> apply(Boolean aBoolean) throws Exception {
                        return getComment("");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommonlyBean<CommentlistBean>>() {
                    @Override
                    public void accept(CommonlyBean<CommentlistBean> beanCommonlyBean) throws Exception {
                        initData();
                        threadGetVideoDetail();
                        ToastUtils.dismiss();
                        isok=true;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ", "");
                        ToastUtils.dismiss();
                    }
                });
    }

    private Observable<CommonlyBean<CommentlistBean>> getComment(String type){
        return Network.getVideoApi().getComment(token,videoModel.getId(),type)
                .doOnNext(new Consumer<CommonlyBean<CommentlistBean>>() {
                    @Override
                    public void accept(CommonlyBean<CommentlistBean> beanCommonlyBean) throws Exception {
                        comments=beanCommonlyBean;
                    }});
    }

    private void showNoNumberDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("香蕉视频")
                .setMessage("您当日没有观看次数了...")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void initData() {
        String video = SPUtils.getString("video", "");
        if (!TextUtils.isEmpty(video)) {
            jc_video.setVisibility(View.GONE);
            iv_ad_img.setVisibility(View.VISIBLE);
            isAding = true;
            Uri uri = Uri.parse(video);
            iv_ad_img.setVideoURI(uri);
            iv_ad_img.start();
            tv_less_second.setVisibility(View.VISIBLE);
            startAdThread();
        } else {
            jc_video.setVisibility(View.VISIBLE);
            tv_less_second.setVisibility(View.GONE);
            iv_ad_img.setVisibility(View.GONE);
            jc_video.startVideo();
        }

        app.setParam(Consts.USER_ISREFRESH_PERSONINFO, USER_FRESH_YES);

        if (videoModel != null) {
            tv_video_name.setText(videoModel.getName());
            tv_content.setText(videoModel.getContent());
            tv_video_date.setText(Utils.getStrDate(String.valueOf(videoModel.getCreate_time())));
            tv_playnum.setText(Utils.getStrPlayNum(videoModel.getViews()));
        }
        if (videoModel.getGoodnum() == 0) {
            percent = 0;
        } else {
            percent = videoModel.getGoodnum()* 100 / (videoModel.getGoodnum() + videoModel.getBadnum());
        }
        progressBarLoad.setVisibility(View.VISIBLE);
        progressBarLoad.setTextBold(true);
        progressBarLoad.setTextSize(28);
        progressBarLoad.setOffset(3);
        progressBarLoad.setProgress(percent);
        tv_zantext.setText("" + percent + "%的人觉得很赞");
        if (0 == typeBean.getLike()) {
            iv_like.setImageResource(R.drawable.icon_dianzan);
        } else {
            iv_like.setImageResource(R.drawable.icon_dianzan_pre);
        }
        if (2 == typeBean.getType()) {
            iv_bad.setImageResource(R.drawable.caied);
        } else if (1 == typeBean.getType()) {
            iv_good.setImageResource(R.drawable.zaned);
        }
        tv_re_comment_count.setText("" + comments.getData().size() + "热评");

        if (!listIsNull(comments.getData())) {
            tv_video_commentnum.setText(comments.getData().size() + "条");
            initCommentAdapter();
        }

        if (!listIsNull(likes)) {
            initLikesAdapter();
        }

        if (!checkLogin()) {
            updateLookNum();
        }
    }

    private int current_time;
    private boolean isAding = false;

    /**
     * 开启广告5秒倒计时
     */
    private void startAdThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isAding) {
                    for (int i = 6; i >= 0; i--) {
                        current_time = i;
                        if (i > 0) {
                            handler.sendEmptyMessage(HADNLER_FRESH_SECOND);
                        } else {
                            isAding = false;
                            handler.sendEmptyMessage(HADNLER_START_PLAY_VIDEO);
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
        thread = null;
    }

    // 更新观看次数
    private void updateLookNum() {
        File file = new File(TXT_LOOK_LESS_NUM);
        if (file.exists() && file.isFile()) {
            String looknum = FileUtils.readAllContent(file);
            if (!TextUtils.isEmpty(looknum)) {
                int intnum = Integer.parseInt(looknum);
                int lessnum = intnum - 1;
                if (lessnum < 0) {
                    app.setParam(Consts.USER_PLAYNUM, "0");
                    ToastUtil.toastShort(mContext, "对不起，您每天的免费观看次数已使用完!");
                    finish();
                    return;
                }
                file.delete();
                LogFile.writeLogToFile(String.valueOf(lessnum), TXT_LOOK_LESS_NUM);
                app.setParam(Consts.USER_PLAYNUM, String.valueOf(lessnum));
            }
        }
    }

    private void initVideoPlayer() {
//        videoUrl = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";
//        iconUrl = "http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b8300-4994-bc87-38f4033806a6.jpg@!640_360";
//        title = "视频播放";
        if (null != videoModel){
            jc_video.setUp(videoModel.getVideofile(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoModel.getName());
            Picasso.with(mContext).load(videoModel.getImage()).into(jc_video.thumbImageView);
            JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        getVideoPlayHeight();
    }

    // 获取视频播放区域高度 计算出Y值
    private void getVideoPlayHeight() {
        height = jc_video.getHeight();
        scro_height = AndroidUtil.dip2px(mContext, height);

    }

    private void initCommentAdapter() {
        commentAdapter = new RecyPingLunAdapter(mContext, comments.getData());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_contents.setNestedScrollingEnabled(false);
        lv_contents.setLayoutManager(manager);
        lv_contents.setAdapter(commentAdapter);

        if (!listIsNull(pldata)) {
            setHeight(mContext, lv_contents, pldata, 120);
        }

    }

    private void initLikesAdapter() {
        recyFilmAdapter = new RecyFilmGengralAdapter(mContext, likes);
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lv_your_likes.setNestedScrollingEnabled(false);
        lv_your_likes.setLayoutManager(manager1);
        lv_your_likes.setAdapter(recyFilmAdapter);
        if (!listIsNull(likes)) {
            setHeight(mContext, lv_your_likes, likes, 130);
        }
    }

    /**
     * 设置高度
     */
    public static void setHeight(Context context, RecyclerView listView, List<?> list, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.height = dip2px(context, height) * list.size();
        listView.setLayoutParams(params);
    }

    private void updataComment(String type){
        Disposable d=getComment(type)
                .compose(this.<CommonlyBean<CommentlistBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<CommentlistBean>>() {
            @Override
            public void accept(CommonlyBean<CommentlistBean> beanCommonlyBean) throws Exception {
                comments=null;
                comments=beanCommonlyBean;
                //commentAdapter.notifyDataSetChanged();
                initCommentAdapter();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setListener() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationListener = new OrientationListener(new OrientationListener.OnOrientationChangeListener() {
            @Override
            public void orientationChanged(int newOrientation) {
                setRequestedOrientation(newOrientation);
            }
        });
        mSensorManager.registerListener(mOrientationListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        rg_video_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_remen:
                        updataComment("");
                        break;
                    case R.id.rb_zuixin:
                        updataComment("new");
                        break;
                }
            }
        });

        scrollView_filmdetail.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                int viewY = llyt_write_feel.getTop();
                LOG.d("sssss", "scrollX" + scrollX + ",scrollY：" + scrollY);
                Log.i("zc11111", "onScrollChange: "+(height));
                if (viewY - scro_height < 0) {
                    if (isShowTop) {
                        llyt_write_feel.setVisibility(View.GONE);
                        llyt_write_feel1.setVisibility(View.VISIBLE);
                        isShowTop = false;
                    }
                } else {
                    if (!isShowTop) {
                        llyt_write_feel.setVisibility(View.VISIBLE);
                        llyt_write_feel1.setVisibility(View.GONE);
                        isShowTop = true;
                    }
                }

                if (scrollY <= 0) {
                    llyt_write_feel.setVisibility(View.VISIBLE);
                    llyt_write_feel1.setVisibility(View.GONE);
                }
            }
        });
        llyt_write_feel1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int viewY = llyt_write_feel1.getTop();
                LOG.i("sssss", "i:" + i + ",i1:" + i1 + ",i2:" + i2 + ",i3:" + i3 + " viewY:" + viewY);
            }
        });
        if (!checkLogin()) {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        }

        rb_zuixin.setOnClickListener(this);
        rb_remen.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        iv_down.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_good.setOnClickListener(this);
        iv_bad.setOnClickListener(this);
//        fullscreen.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.fullscreen:
//                JCVideoPlayerStandard.startFullscreen(mContext, JCVideoPlayerStandard.class, videoUrl, title);
//                break;
            case R.id.iv_like:
                if (!checkLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    if (typeBean==null){
                        return;
                    }
                    if (typeBean.getLike()==0) {
                        collectLike();
                    }else {
                        cancelLike();
                    }
                }
                break;
            case R.id.iv_down:
                if (!checkLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    DownBll downBll = new DownBll(mContext);
                    VideoDownDBModel dalmodel = null;
                    if (videoModel != null) {
                        dalmodel = downBll.getByVideoId(String.valueOf(videoModel.getId()));
                    } else {
                        if (!TextUtils.isEmpty(String.valueOf(videoModel.getId()))) {
                            dalmodel = downBll.getByVideoId(String.valueOf(videoModel.getId()));
                        }
                    }
                    if (dalmodel != null) {
                        mContext.startActivity(new Intent(mContext, DownListActivity.class));
                        return;
                    }

                    ToastUtil.toastShort(mContext, "开始下载");

                    VideoDownDBModel newmodel = new VideoDownDBModel();
                    newmodel.setVideo_id(videoModel.getId());
                    newmodel.setFilePath(Consts.VIDEO_CACHE_DIR + Utils.getFileName(videoModel.getVideofile()));
                    newmodel.setImage(videoModel.getImage());
                    newmodel.setName(videoModel.getName());
                    newmodel.setDownurl(videoModel.getVideofile());
                    newmodel.setStatus(VideoDownDBModel.DOWN_STATUS_DOWNING);
                    newmodel.setCreatetime(DcDateUtils.now());
                    downBll.save(newmodel);

                    VideoDownDBModel byVideoId = downBll.getByVideoId(String.valueOf(newmodel.getVideo_id()));

//                if (!downBll.isLocalVideoExists(video_id)) {
                    downBll.downLoadFile(byVideoId);
//                }
                    startActivity(new Intent(mContext, DownListActivity.class));
                }
                break;
            case R.id.iv_share:
                if (!checkLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    share();
                }
                break;
            case R.id.iv_good:
                if (!checkLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    if (typeBean==null){
                        return;
                    }
                    if (typeBean.getType()==0){
                        zan(1);
                    }else {
                        Toast.makeText(mContext, "您已经评价过了...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_bad:
                if (!checkLogin()) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    if (typeBean==null){
                        return;
                    }
                    if (typeBean.getType()==0){
                        zan(0);
                    }else {
                        Toast.makeText(mContext, "您已经评价过了...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Event(R.id.llyt_brief)
    private void showBriefDialog(View v) {
        if (isok){
            showJianjDialog();
        }
    }

    @Event(R.id.tv_send_comment)
    private void showSendDialog(View v) {
        showSendCommentDialog();
    }

    private DialogFilmBrief dialogFilmBrief;
    private DialogSendComment dialogSendComment;

    /**
     * 弹出简介dialog
     */
    private void showJianjDialog() {
        if (videoModel==null){
            return;
        }
        int height = jc_video.getHeight();

        dialogFilmBrief = new DialogFilmBrief(mContext, height, videoModel,label);
        dialogFilmBrief.setCanceledOnTouchOutside(true);
        dialogFilmBrief.setView(new EditText(mContext));
        dialogFilmBrief.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogFilmBrief.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialogFilmBrief.getWindow().setAttributes(lp);
    }

    private VideoContentBean commentMode;

    private void showSendCommentDialog() {
        int height = jc_video.getHeight();

        dialogSendComment = new DialogSendComment(mContext, height);
        dialogSendComment.setCanceledOnTouchOutside(true);
        dialogSendComment.setView(new EditText(mContext));
        dialogSendComment.show();

        dialogSendComment.setiSendComment(new DialogSendComment.ISendComment() {
            @Override
            public void onClickSendComment(String comment) {
                commentMode = getModel(comment);
                publishComment(uid, videoModel.getId() + "", comment);
            }
        });
//        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogSendComment.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialogSendComment.getWindow().setAttributes(lp);
    }

    private VideoContentBean getModel(String comment) {
        VideoContentBean model = new VideoContentBean();
        if (app != null) {
            if (app.getParam(Consts.USER_HEADIMG) != null) {
                model.setAvatar(app.getParam(Consts.USER_HEADIMG).toString());
            }
            if (app.getParam(Consts.USER_NICK_NAME) != null) {
                model.setNickname(app.getParam(Consts.USER_NICK_NAME).toString());
            }
            if (app.getParam(Consts.USER_SEX) != null) {
                model.setSex(app.getParam(Consts.USER_SEX).toString());
            }
            if (app.getParam(Consts.USER_UID) != null) {
                model.setUser_id(Integer.parseInt(app.getParam(Consts.USER_UID).toString()));
            }
            model.setComment(comment);
            model.setCreate_time(String.valueOf(DcDateUtils.now().getTime()));
        }
        return model;
    }

    public int percent;

    private void threadGetVideoDetail() {
        label = (HashMap<Object, String>) DcAndroidContext.getInstance().getParam("label");
        if (label == null) {
            label = new HashMap<>();
            Network.getVideoApi().getLabel_index()
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<CommonlyBean<LabelBean>, Publisher<LabelBean>>() {
                        @Override
                        public Publisher<LabelBean> apply(CommonlyBean<LabelBean> labelBeanCommonlyBean) throws Exception {
                            List<LabelBean> data = labelBeanCommonlyBean.getData();
                            return Flowable.fromIterable(data);
                        }
                    }).flatMap(new Function<LabelBean, Publisher<LabelBean.ChildrenBean>>() {
                @Override
                public Publisher<LabelBean.ChildrenBean> apply(LabelBean labelBean) throws Exception {
                    return Flowable.fromIterable(labelBean.getChildren());
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LabelBean.ChildrenBean>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(Long.MAX_VALUE);
                        }

                        @Override
                        public void onNext(LabelBean.ChildrenBean bean) {
                            label.put(bean.getId(), bean.getName());
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.e("onError: ","" );
                        }

                        @Override
                        public void onComplete() {
                            Log.e("onNext: ", "结束--->" + label.size());
                            DcAndroidContext.getInstance().setParam("label", label);
                        }
                    });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ToastUtils.dismiss();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getLikes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkGo.<String>post(Consts.ACTION_URL_GET_DEF_LIKES).tag(this)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    if (200 == response.code() && !TextUtils.isEmpty(response.body())) {
                                        VideoLikeResultBean bean = GsonUtil.parseJsonWithGson(response.body(), VideoLikeResultBean.class);
                                        if (bean != null) {
                                            likes = bean.getData();
                                            if (!listIsNull(likes)) {
                                                initLikesAdapter();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread = null;
    }

    private void publishComment(final String uid, final String video_id, final String comment) {
        ProgressDialogUtil.showDialog(mContext, "发布评论", true);
        Log.i("zengchong", "publishComment: "+token);
        Disposable d=Network.getVideoApi().setComment(token,String.valueOf(videoModel.getId()),comment)
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject json=new JSONObject(responseBody.string());
                        if (json.getInt("code")==200) {
                            Toast.makeText(PlayVideoDetailActivity.this, "评价成功!", Toast.LENGTH_SHORT).show();
                            updataComment("new");
                            ProgressDialogUtil.dismiss();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismiss();
                        Toast.makeText(PlayVideoDetailActivity.this, "评价失败，", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    // 添加到我的喜欢
    private void collectLike() {
        Disposable d=Network.getVideoApi().setLike(token,String.valueOf(videoModel.getId()))
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject=new JSONObject(responseBody.string());
                        if (jsonObject.getInt("code")==200) {
                            ToastUtil.toastShort(mContext, "已添加到我的喜欢!");
                            iv_like.setImageResource(R.drawable.icon_dianzan_pre);
                            typeBean.setLike(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });
    }

    // 取消喜欢
    private void cancelLike() {
        Disposable d=Network.getVideoApi().cancelLike(token,String.valueOf(videoModel.getId()))
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject=new JSONObject(responseBody.string());
                        if (jsonObject.getInt("code")==200) {
                            ToastUtil.toastShort(mContext, "取消成功!");
                            iv_like.setImageResource(R.drawable.icon_dianzan);
                            typeBean.setLike(0);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    // 点赞
    private void zan(final int status) {
        Disposable d=Network.getVideoApi().setEvaluate(token,videoModel.getId(),status)
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject json=new JSONObject(responseBody.string());
                        String msg=json.getString("msg");
                        if (json.getInt("code")==200){
                            if (status==1) {
                                //ToastUtil.toastShort(mContext, msg+"");
                                iv_good.setImageResource(R.drawable.zaned);
                                typeBean.setType(1);
                                percent = (videoModel.getGoodnum()+1)* 100/ (videoModel.getGoodnum()+1 + videoModel.getBadnum());
                            }else {
                                iv_bad.setImageResource(R.drawable.caied);
                                typeBean.setType(2);
                                percent = videoModel.getGoodnum()* 100 / (videoModel.getGoodnum() +1+ videoModel.getBadnum());
                            }
                            progressBarLoad.setProgress(percent);
                            tv_zantext.setText("" + percent + "%的人觉得很赞");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    // 下载
    private void share() {
        shareMsg("香蕉视频", "分享", "大家一起来看香蕉视频吧", null);
    }

    /**
     * 分享功能
     *
     * @param strTitle   分享弹出框标题
     * @param strSubject 消息标题
     * @param strMsgText 消息内容
     * @param strImgPath 图片路径，不分享图片则传null
     */
    public void shareMsg(String strTitle, String strSubject, String strMsgText, String strImgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (strImgPath == null || strImgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(strImgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");//图片形式
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);//未用到
        intent.putExtra(Intent.EXTRA_TEXT, strMsgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, strTitle));
    }


    private class TypeBean{


        /**
         * uid : 9
         * vid : 14
         * create_time : 1555051642
         * like : 0
         * type : 1
         * times : 0
         */

        private int uid;
        private String vid;
        private int create_time;
        private int like;
        private int type;
        private String times;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
