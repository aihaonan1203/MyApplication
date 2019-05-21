package com.zcf.bananavideohannan.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.AndroidUtil;
import com.gaotai.baselib.util.LoadImageUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.master.permissionhelper.PermissionHelper;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.AllFilmActivity;
import com.zcf.bananavideohannan.activity.SearchActivity;
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.activity.my.LookHistoryActivity;
import com.zcf.bananavideohannan.adapter.DefBestNewAdapter;
import com.zcf.bananavideohannan.adapter.DefReboAdapter;
import com.zcf.bananavideohannan.adapter.GTPictureDetailAdapter;
import com.zcf.bananavideohannan.adapter.LvCustomFilmsAdapter;
import com.zcf.bananavideohannan.adapter.RevyLikesAdapter;
import com.zcf.bananavideohannan.adapter.VideoTypeAdaptger;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.CustomFilmBean;
import com.zcf.bananavideohannan.bean.DefTypeBean;
import com.zcf.bananavideohannan.bean.ReboBean;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.domain.AdDomain;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.MyGridViewNoScroll;
import com.zcf.bananavideohannan.view.ToastViewDialog;
import com.zcf.bananavideohannan.view.ViewPagerCustom;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 首页
 */
@ContentView(R.layout.fragment_main_def)
public class MainDefFragment extends MyBaseFragment implements View.OnClickListener {
    private GTPictureDetailAdapter adapter;
    private VideoTypeAdaptger videoTypeAdaptger;

    private RadioGroup rg_point;

    private GridView gv_videoType;// 片源类别

    private MyGridViewNoScroll gv_besenew;// 最新片源

    private MyGridViewNoScroll gv_rebo;// 重磅热播
    private MyGridViewNoScroll gv_custom_films1;// 自定义1
    private MyGridViewNoScroll gv_custom_films2;// 自定义2

    private ViewPagerCustom vierpager_ad;// 广告
    private ImageView iv_ad_img_center;
    private ImageView iv_ad_img_bottom;
    private TextView tv_ad_center_content;
    private TextView tv_ad_bottom_content;
    private RecyclerView recy_your_likes;
    private RelativeLayout rlyt_search;
    private ImageView iv_lsjl;
    private ImageView iv_down;
    private PullToRefreshScrollView smartRefreshLayout;
    private ListView lv_custom_films;
    private LinearLayout llyt_get_more;
    private LinearLayout llyt_get_rebo;
    private ImageView iv_capture;
    private RelativeLayout rlyt_fresh_data;


    private DefBestNewAdapter defAdapter1;
    private DefReboAdapter defAdapter2;

    private DefBestNewAdapter defAdapter3;
    private DefBestNewAdapter defAdapter4;
    private RevyLikesAdapter likesAdapter;
    private LvCustomFilmsAdapter customFilmsAdapter;

    private DcAndroidContext app;
    private boolean isGetAd = false;// 是否获取广告
    private boolean isGetType = true;// 是否获取类型
    private boolean isGetBeseNew = true;// 是否获取最新片源
    private boolean isGetRebo = true;// 是否获取热播
    private boolean isGetLikes = true;// 是否获取猜你喜欢
    private boolean isGetCustMode = true;// 是否获取自定义模板数据

    private final int HANDLER_INIT_ADAPTER_TOPTYPE = 1;
    private final int HANDLER_INIT_ADAPTER_BESTNEW = 2;
    private final int HANDLER_INIT_ADAPTER_REBO = 3;
    private final int HANDLER_INIT_ADAPTER_LIKES = 4;
    private final int HANDLER_INIT_ADAPTER_CUSTTHEME = 5;
    private final int HANDLER_INIT_ADAPTER_AD = 6;
    private final int REQEST_CAPTURE_QR = 7;
    private final int HANDLER_INIT_ADAPTER_HOT = 8;
    private final int HANDLER_INIT_ADAPTER_CUSTOM_TAG = 9;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshComplete();
            if (toastViewDialog != null) {
                toastViewDialog.dismiss();
            }

            if (msg.what == HANDLER_INIT_ADAPTER_TOPTYPE) {
                app.setParam(AllFilmActivity.EXTRA_LIST_TYPE_DATA, data_top_type);
                initToptypeAdapter(data_top_type);
            } else if (msg.what == HANDLER_INIT_ADAPTER_BESTNEW) {
                initZuixinAdapter(data_bestnew);
//                initLikesAdapter(data_likes);
            } else if (msg.what == HANDLER_INIT_ADAPTER_REBO) {
//                initReboAdapter(data_rebo);
            } else if (msg.what == HANDLER_INIT_ADAPTER_LIKES) {
                initLikesAdapter(likeBean);
            } else if (msg.what == HANDLER_INIT_ADAPTER_CUSTTHEME) {
                initTopAdImgAdapter(adlink);
            } else if (msg.what == HANDLER_INIT_ADAPTER_AD) {

            } else if (msg.what == HANDLER_INIT_ADAPTER_HOT) {
                initReboAdapter(hotBean);
            } else if (msg.what == HANDLER_INIT_ADAPTER_CUSTOM_TAG) {
                initCustMobanAdapter(customTagBean);
            }
        }
    };

    List<ReboBean.DataBean> data_bestnew;
    List<CustomFilmBean.DataBean> adlink;
    private PermissionHelper permissionHelper;
    private List<ReboBean.DataBean> hotBean;
    private List<ReboBean.DataBean> likeBean;
    private List<ZltjTjResultBean.DataBean> customTagBean;
    private List<CustomFilmBean.DataBean> guanGaoBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_def, null);

        rg_point = view.findViewById(R.id.rg_point);
        gv_videoType = view.findViewById(R.id.gv_videoType);
        gv_besenew = view.findViewById(R.id.gv_besenew);
        gv_rebo = view.findViewById(R.id.gv_rebo);
        vierpager_ad = view.findViewById(R.id.vierpager_ad);
        recy_your_likes = view.findViewById(R.id.gv_your_likes);
        lv_custom_films = view.findViewById(R.id.lv_custom_films);
        rlyt_search = view.findViewById(R.id.rlyt_search);
        iv_lsjl = view.findViewById(R.id.iv_lsjl);
        llyt_get_rebo = view.findViewById(R.id.llyt_get_rebo);
        iv_down = view.findViewById(R.id.iv_down);
        iv_ad_img_center = view.findViewById(R.id.iv_ad_img_center);
        iv_ad_img_bottom = view.findViewById(R.id.iv_ad_img_bottom);
        iv_capture = view.findViewById(R.id.iv_capture);
        rlyt_fresh_data = view.findViewById(R.id.rlyt_fresh_data);

        tv_ad_center_content = view.findViewById(R.id.tv_ad_center_content);
        tv_ad_bottom_content = view.findViewById(R.id.tv_ad_bottom_content);

        smartRefreshLayout = view.findViewById(R.id.refreshlayout);
        llyt_get_more = view.findViewById(R.id.llyt_get_more);

        app = (BvAndroidContent) getActivity().getApplicationContext();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        setListeners();
    }

    List<DefTypeBean.DataBean> data_top_type;

    // 2.获取首页顶部分类数据
    private void loadDefData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGo.<String>post(Consts.ACTION_URL_GETALL_TYPE).tag(this).isMultipart(false)
                        .params("type", "default")
                        .params("flag", "index")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        DefTypeBean bean = GsonUtil.parseJsonWithGson(response.body(), DefTypeBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                // 获取成功
                                                data_top_type = bean.getData();
                                                if (!listIsNull(data_top_type)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_TOPTYPE);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_ZUIXIN).tag(this)
                        .params("page", 1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        ReboBean bean = GsonUtil.parseJsonWithGson(response.body(), ReboBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                // 获取成功
                                                data_bestnew = bean.getData();
                                                if (!listIsNull(data_bestnew)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_BESTNEW);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_REBO).tag(this)
                        .params("page", 1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        ReboBean bean = GsonUtil.parseJsonWithGson(response.body(), ReboBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                hotBean = bean.getData();
                                                // 获取成功
                                                if (!listIsNull(hotBean)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_HOT);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_LIKES).tag(this)
                        .params("page", 1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        ReboBean bean = GsonUtil.parseJsonWithGson(response.body(), ReboBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                likeBean = bean.getData();
                                                // 获取成功
                                                if (!listIsNull(likeBean)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_LIKES);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });


                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_CUST).tag(this).isMultipart(false)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        CustomFilmBean bean = GsonUtil.parseJsonWithGson(response.body(), CustomFilmBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                // 获取成功
                                                adlink = bean.getData();
                                                if (!listIsNull(adlink)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_CUSTTHEME);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_CUST2).tag(this).isMultipart(false)
                        .params("page", 1)
                        .params("flag", "index")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        ZltjTjResultBean bean = GsonUtil.parseJsonWithGson(response.body(), ZltjTjResultBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                // 获取成功
                                                customTagBean = bean.getData();
                                                if (!listIsNull(customTagBean)) {
                                                    handler.sendEmptyMessage(HANDLER_INIT_ADAPTER_CUSTOM_TAG);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

                OkGo.<String>post(Consts.ACTION_URL_GET_DEF_GUANGGAO).tag(this).isMultipart(false)
                        .params("type", "index")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (200 == response.code()) {
                                        CustomFilmBean bean = GsonUtil.parseJsonWithGson(response.body(), CustomFilmBean.class);
                                        if (bean != null) {
                                            if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                                // 获取成功
                                                guanGaoBean = bean.getData();
                                                if (!listIsNull(guanGaoBean)) {
                                                    iv_ad_img_center.setVisibility(View.VISIBLE);
                                                    LoadImageUtil.loadImg(guanGaoBean.get(0).getImage(), iv_ad_img_center, LoadImageUtil.getImageOptions(R.drawable.meinv), R.drawable.meinv);
                                                    if (guanGaoBean.get(1)!=null){
                                                        iv_ad_img_bottom.setVisibility(View.VISIBLE);
                                                        LoadImageUtil.loadImg(guanGaoBean.get(1).getImage(), iv_ad_img_bottom, LoadImageUtil.getImageOptions(R.drawable.meinv), R.drawable.testimg1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });

            }
        }).start();
    }

    ToastViewDialog toastViewDialog;

    /**
     * 获取首页数据
     */
    private void loadData() {
        toastViewDialog = new ToastViewDialog(getActivity(), "加载中，请稍后...");
        toastViewDialog.show();
        if (isGetType) {
            loadDefData();
        }

    }

    private AdDomain centerDomain;
    private AdDomain bottomDomain;

    // 顶部广告页
    private void openBrowser(String linkUrl) {
        Uri uri = Uri.parse(linkUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void initTopAdImgAdapter(List<CustomFilmBean.DataBean> adlink) {
        if (adlink == null) {
            adlink = new ArrayList<>();
        }
        if (rg_point != null) {
            rg_point.removeAllViews();
        }


        for (int i = 0; i < adlink.size(); i++) {
            RadioButton rbtn = new RadioButton(getActivity());
            rbtn.setButtonDrawable(null);
            rbtn.setBackgroundResource(R.drawable.radiobutton_ischecked);
            rbtn.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            rbtn.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            rg_point.addView(rbtn);
        }
        RadioButton rbtn = (RadioButton) rg_point.getChildAt(0);
        rbtn.setChecked(true);


        adapter = new GTPictureDetailAdapter(getActivity().getSupportFragmentManager(), adlink);
        vierpager_ad.setAdapter(adapter);

        if (centerDomain != null && !TextUtils.isEmpty(centerDomain.getImage())) {
            iv_ad_img_center.setVisibility(View.VISIBLE);
            LoadImageUtil.loadImg(centerDomain.getImage(), iv_ad_img_center, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            if (!TextUtils.isEmpty(centerDomain.getAdlink())) {
                iv_ad_img_center.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBrowser(centerDomain.getAdlink());
                    }
                });
            }
        }

        if (bottomDomain != null && !TextUtils.isEmpty(bottomDomain.getImage())) {
            iv_ad_img_bottom.setVisibility(View.VISIBLE);
            LoadImageUtil.loadImg(bottomDomain.getImage(), iv_ad_img_bottom, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            if (!TextUtils.isEmpty(bottomDomain.getAdlink())) {
                iv_ad_img_bottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBrowser(bottomDomain.getAdlink());
                    }
                });
            }
        }
    }

    // 顶部类型adaptger
    private void initToptypeAdapter(List<DefTypeBean.DataBean> data) {
        List<DefTypeBean.DataBean> datas=new ArrayList<>();
        if (data.size() > 7) {
            for (int i = 0; i < 7; i++) {
                if (data.get(i)!=null) {
                    datas.add(data.get(i));
                }
            }
        }
        datas.add(new DefTypeBean.DataBean(0, "全部"));
        if (videoTypeAdaptger == null) {
            videoTypeAdaptger = new VideoTypeAdaptger(getActivity(), datas);
            gv_videoType.setAdapter(videoTypeAdaptger);
        } else {
            videoTypeAdaptger.setData(data);
            videoTypeAdaptger.notifyDataSetChanged();
        }
    }

    // 最新片源adapter
    private void initZuixinAdapter(List<ReboBean.DataBean> data) {
        if (defAdapter1 == null) {
            defAdapter1 = new DefBestNewAdapter(getActivity(), data);
            gv_besenew.setAdapter(defAdapter1);
        } else {
            defAdapter1.setData(data);
            defAdapter1.notifyDataSetChanged();
        }
    }

    //热播
    private void initReboAdapter(List<ReboBean.DataBean> data) {
        if (defAdapter2 == null) {
            defAdapter2 = new DefReboAdapter(getActivity(), data);
            gv_rebo.setAdapter(defAdapter2);
        } else {
            defAdapter2.setData(data);
            defAdapter2.notifyDataSetChanged();
        }
    }

    // 猜你喜欢adapter
    private void initLikesAdapter(List<ReboBean.DataBean> data) {
        likesAdapter = new RevyLikesAdapter(getActivity(), data);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_your_likes.setLayoutManager(manager);
        recy_your_likes.setAdapter(likesAdapter);
    }

    // 自定义adapter
    private void initCustMobanAdapter(List<ZltjTjResultBean.DataBean> data) {
        if (customFilmsAdapter == null) {
            customFilmsAdapter = new LvCustomFilmsAdapter(getActivity(), data);
            lv_custom_films.setAdapter(customFilmsAdapter);
        } else {
            customFilmsAdapter.setData(data);
            customFilmsAdapter.notifyDataSetChanged();
        }
    }

    private void setListeners() {
        vierpager_ad.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                RadioButton rbtn = (RadioButton) rg_point.getChildAt(position);
                rbtn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        rlyt_search.setOnClickListener(this);
        iv_lsjl.setOnClickListener(this);
        llyt_get_more.setOnClickListener(this);
        llyt_get_rebo.setOnClickListener(this);
        iv_capture.setOnClickListener(this);
        iv_down.setOnClickListener(this);
        rlyt_fresh_data.setOnClickListener(this);


        //下拉内容不偏移
        smartRefreshLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout headLables = smartRefreshLayout.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel("下拉刷新");
        headLables.setRefreshingLabel("哎呀，再等一会吗，不要急撒");
        headLables.setReleaseLabel("有一种爱叫做放手，松开我就刷新了");
        smartRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadDefData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });
    }

    public void refreshComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smartRefreshLayout.onRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlyt_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_lsjl:
                startActivity(new Intent(getActivity(), LookHistoryActivity.class));
                break;
            case R.id.llyt_get_rebo:
            case R.id.llyt_get_more:
                Bundle bundle = new Bundle();
                bundle.putInt(AllFilmActivity.EXTRA_TYPE_ID, -1);
                bundle.putString(AllFilmActivity.EXTRA_TYPE_SORT, "全部");
                Intent intent = new Intent(getActivity(), AllFilmActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_capture:
                gotoCapture();
                break;
            case R.id.iv_down:
                startActivity(new Intent(getActivity(), DownListActivity.class));
//                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            case R.id.rlyt_fresh_data:
                loadData();
                break;
        }
    }

    private void gotoCapture() {
        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.CAMERA}, 100);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQEST_CAPTURE_QR);
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
//                 Log.d(TAG, "onIndividualPermissionGranted() called with: grantedPermission = [" + TextUtils.join(",",grantedPermission) + "]");
            }

            @Override
            public void onPermissionDenied() {
//                 Log.d(TAG, "onPermissionDenied() called");
            }

            @Override
            public void onPermissionDeniedBySystem() {
//                 Log.d(TAG, "onPermissionDeniedBySystem() called");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQEST_CAPTURE_QR:
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    if (!TextUtils.isEmpty(content)) {
                        AndroidUtil.openBrowser(getActivity(), content);
                    }
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
