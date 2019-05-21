package com.zcf.bananavideohannan.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.log.LogFile;
import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.util.FileUtils;
import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.NoticeActivity;
import com.zcf.bananavideohannan.activity.my.AccountManagerActivity;
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.activity.my.FeedBackActivity;
import com.zcf.bananavideohannan.activity.my.LookHistoryActivity;
import com.zcf.bananavideohannan.activity.my.MyLikesActivity;
import com.zcf.bananavideohannan.activity.my.SettinsActivity;
import com.zcf.bananavideohannan.activity.my.TuiGuangActivity;
import com.zcf.bananavideohannan.activity.my.VipActivity;
import com.zcf.bananavideohannan.adapter.MyRecyDownAdapter;
import com.zcf.bananavideohannan.adapter.MyRecyHistoryAdapter;
import com.zcf.bananavideohannan.adapter.MyRecyLikeAdapter;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.LikeBean;
import com.zcf.bananavideohannan.bean.LishiBean;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.OfflineCacheBean.OffCacheBean;
import com.zcf.bananavideohannan.bll.DownBll;
import com.zcf.bananavideohannan.bll.LoginBll;
import com.zcf.bananavideohannan.bll.SettingBll;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;
import com.zcf.bananavideohannan.evenMsg.LikeEvent;
import com.zcf.bananavideohannan.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

import static com.zcf.bananavideohannan.base.Consts.USER_FRESH_NO;
import static com.zcf.bananavideohannan.base.Consts.USER_FRESH_YES;
import static com.zcf.bananavideohannan.base.Consts.USER_ISREFRESH_PERSONINFO;
import static com.zcf.bananavideohannan.base.Consts.VIDEO_CACHE_DIR;

/**
 * 我的
 */
public class MyFragment extends MyBaseFragment implements View.OnClickListener {

    private ImageView iv_headimg;// 头像

    private TextView tv_vip_duihuan;// 兑换

    private TextView tv_vip_lv;// vip等级
    private RelativeLayout my_p_cishu;//
    private RelativeLayout my_p_dengji;//

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;// 电话
    private ImageView iv_sex;// 性别
    private ImageView my_p_grade_img;
    private TextView tv_look_count;// 观看次数
    private TextView my_p_txt_lishijilu_msg;// 观看记录数
    private TextView my_p_txt_huancun_msg;// 缓存数
    private TextView my_p_txt_xihuan_msg;// 喜欢数
    private TextView tv_less_count;// 下一等级差人
    private LinearLayout llyt_tuiguang;// 推广
    private LinearLayout llyt_feedback;// 反馈
    private LinearLayout llyt_tongzhi;// 通知
    private LinearLayout llyt_jiaoliuqun;// 交流群
    private RelativeLayout rlyt_my_lishijilu;// 历史记录
    private RecyclerView rcv_history;// 历史记录
    private RelativeLayout rlyt_my_huancun;// 缓存
    private RecyclerView rcv_downlist;// 历史记录
    private RelativeLayout rlyt_my_xihuan;// 我的喜欢
    private RecyclerView rcv_mylikes;// 历史记录
    private LinearLayout llyt_perinfo;//
    private TextView tv_please_login;//

    private ImageView iv_settiing;
    private Context mContext;
    private BvAndroidContent app;
    private String uid;
    private DownBll downBll;

    private MyRecyHistoryAdapter historyAdapter;
    private MyRecyLikeAdapter myLikeAdapter;
    private MyRecyDownAdapter myDownAdapter;
    private String url;
    private String contact;
    private String shroff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_my, null);
        mContext = getActivity();
        downBll = new DownBll(mContext);
        iv_headimg = view.findViewById(R.id.iv_headimg);
        llyt_perinfo = view.findViewById(R.id.llyt_perinfo);
        tv_please_login = view.findViewById(R.id.tv_please_login);
        tv_vip_duihuan = view.findViewById(R.id.tv_vip_duihuan);
        tv_vip_lv = view.findViewById(R.id.tv_vip_lv);
        tv_phone = view.findViewById(R.id.tv_phone);
        iv_sex = view.findViewById(R.id.iv_sex);
        my_p_grade_img = view.findViewById(R.id.my_p_grade_img);
        tv_look_count = view.findViewById(R.id.tv_look_count);
        tv_less_count = view.findViewById(R.id.tv_less_count);
        llyt_tuiguang = view.findViewById(R.id.llyt_tuiguang);
        llyt_feedback = view.findViewById(R.id.llyt_feedback);
        llyt_tongzhi = view.findViewById(R.id.llyt_tongzhi);
        llyt_jiaoliuqun = view.findViewById(R.id.llyt_jiaoliuqun);
        rlyt_my_lishijilu = view.findViewById(R.id.rlyt_my_lishijilu);
        rlyt_my_huancun = view.findViewById(R.id.rlyt_my_huancun);
        rlyt_my_xihuan = view.findViewById(R.id.rlyt_my_xihuan);
        iv_settiing = view.findViewById(R.id.iv_settiing);
        my_p_txt_lishijilu_msg = view.findViewById(R.id.my_p_txt_lishijilu_msg);
        my_p_txt_huancun_msg = view.findViewById(R.id.my_p_txt_huancun_msg);
        my_p_txt_xihuan_msg = view.findViewById(R.id.my_p_txt_xihuan_msg);
        my_p_cishu = view.findViewById(R.id.my_p_cishu);
        my_p_dengji = view.findViewById(R.id.my_p_dengji);

        rcv_history = view.findViewById(R.id.rcv_histpry);
        rcv_downlist = view.findViewById(R.id.rcv_downlist);
        rcv_mylikes = view.findViewById(R.id.rcv_mylikes);

        app = (BvAndroidContent) mContext.getApplicationContext();
        app.setParam(USER_ISREFRESH_PERSONINFO, USER_FRESH_NO);

        setListeners();
        Disposable d=Network.getVideoApi().exchange()
                .compose(MyBaseFragment.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject=new JSONObject(responseBody.string());
                        JSONObject data = (JSONObject) jsonObject.getJSONArray("data").get(0);
                        url = data.getString("exchange_url");
                        contact = data.getString("contact");
                        shroff = data.getString("shroff");
                        DcAndroidContext.getInstance().setParam("contact",contact);
                        DcAndroidContext.getInstance().setParam("shroff",shroff);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });
        return view;
    }

    private void getVXInfo() {
        SettingBll settingBll = new SettingBll(getActivity());
        settingBll.getVXInfo();
    }

    @Override
    public void onResume() {
        if (app != null && app.getParam(USER_ISREFRESH_PERSONINFO) != null) {
            String yesOrno = app.getParam(USER_ISREFRESH_PERSONINFO).toString();
            if (USER_FRESH_YES.equals(yesOrno)) {
                if (app.getParam(Consts.USER_UID) != null) {
//                loadPersonInfo();
                    getPerInfo(app.getParam(ContextProperties.REM_UID).toString());
                }
            }
        }

        readPersonInfoFromRem();

        List<VideoDownDBModel> list = downBll.getList();
        if (!listIsNull(list)) {
            my_p_txt_huancun_msg.setText("目前本地大片有" + list.size() + "部");
        } else {
            my_p_txt_huancun_msg.setText("目前本地大片有0部");
        }
        getVXInfo();

        if (app != null) {
            if (app.getHistoryData() != null) {
                List<VideoDownDBModel> downs = downBll.getList();
                if (!listIsNull(downs)) {
                    rcv_downlist.setVisibility(View.VISIBLE);
                    initDownAdapter(downs);
                } else {
                    rcv_downlist.setVisibility(View.GONE);
                }
            }
        }

        getMyLikesData();
        getHistoryData();
        super.onResume();
    }


    private void getMyLikesData() {
        Disposable d=Network.getVideoApi().getMyLike(userinfo.getToken())
                .compose(this.<CommonlyBean<LikeBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<LikeBean>>() {
                    @Override
                    public void accept(CommonlyBean<LikeBean> likeBeanCommonlyBean) throws Exception {
                        EventBus.getDefault().postSticky(new LikeEvent("like",likeBeanCommonlyBean.getData()));
                        if (likeBeanCommonlyBean.getData()!=null&&likeBeanCommonlyBean.getData().size()>0) {
                            rcv_mylikes.setVisibility(View.VISIBLE);
                            my_p_txt_xihuan_msg.setText("目前已有喜欢" + likeBeanCommonlyBean.getData().size() + "部");
                            initLikesAdapter(likeBeanCommonlyBean.getData());
                        }else {
                            my_p_txt_xihuan_msg.setText("目前已有喜欢0部");
                            initLikesAdapter(new ArrayList<LikeBean>());
                            rcv_mylikes.setVisibility(View.GONE);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getHistoryData() {
        Disposable d=Network.getVideoApi().getFootprint(userinfo.getToken(),"4")
                .compose(this.<CommonlyBean<LishiBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<LishiBean>>() {
                    @Override
                    public void accept(CommonlyBean<LishiBean> lishiBeanCommonlyBean) throws Exception {
                        if (lishiBeanCommonlyBean.getData()!=null&&lishiBeanCommonlyBean.getData().size()>0){
                            my_p_txt_lishijilu_msg.setText("目前历史记录观看过" + lishiBeanCommonlyBean.getData().size() + "部");
                            rcv_history.setVisibility(View.VISIBLE);
                            initHistoryAdapter(lishiBeanCommonlyBean.getData());
                        }else{
                            my_p_txt_lishijilu_msg.setText("目前历史记录观看过0部");
                            initHistoryAdapter(new ArrayList<LishiBean>());
                            rcv_history.setVisibility(View.GONE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private long svenTime = 7 * 24 * 60 * 60;
    private long oneTime = 24 * 60 * 60;

    private void toData(List<OffCacheBean.DataBeanX.DataBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        app.setParam(Consts.MY_HISTORY_COUNT, data.size());
        if (!listIsNull(data)) {
            app.getHistoryData().put(BvAndroidContent.APP_ALL_HISTORY_DATA, data);

            List<OffCacheBean.DataBeanX.DataBean> todayData = new ArrayList<>();
            List<OffCacheBean.DataBeanX.DataBean> sevenData = new ArrayList<>();
            List<OffCacheBean.DataBeanX.DataBean> moreData = new ArrayList<>();
            long currentTime = DcDateUtils.now().getTime() / 1000;
            for (int i = 0; i < data.size(); i++) {
                OffCacheBean.DataBeanX.DataBean model = data.get(i);
                long time = Long.parseLong(model.getCreate_time());
                long lesstime = currentTime - time;
                if (lesstime > 0 && lesstime < oneTime) {
                    todayData.add(model);
                } else if (lesstime > oneTime && lesstime < svenTime) {
                    sevenData.add(model);
                } else if (lesstime > svenTime) {
                    moreData.add(model);
                }
            }

            app.getHistoryData().put(BvAndroidContent.APP_TODAY, todayData);
            app.getHistoryData().put(BvAndroidContent.APP_SWVEN, sevenData);
            app.getHistoryData().put(BvAndroidContent.APP_MORE, moreData);
        }
    }

    private void readPersonInfoFromRem() {
        if (app == null) {
            app = (BvAndroidContent) getActivity().getApplicationContext();
        }
        if (app.getParam(ContextProperties.REM_UID) != null) {
            uid = app.getParam(ContextProperties.REM_UID).toString();
        }

        if (checkLogin()) {
            llyt_perinfo.setVisibility(View.VISIBLE);
            tv_please_login.setVisibility(View.GONE);
            initData(userinfo);
        } else {
            llyt_perinfo.setVisibility(View.GONE);
            tv_please_login.setVisibility(View.VISIBLE);
            tv_less_count.setText("去推广升级吧");
            tv_vip_lv.setText("登录注册");


            File file = new File(Consts.TXT_LOOK_LESS_NUM);
            if (!file.exists()) {
                try {
                    File filefir = new File(VIDEO_CACHE_DIR);
                    if (!filefir.exists()) {
                        filefir.mkdirs();
                    }

                    file.createNewFile();
                    LogFile.writeLogToFile(String.valueOf(5), Consts.TXT_LOOK_LESS_NUM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            tv_look_count.setText(String.valueOf(userinfo.getMoney()));
        }
    }
    private LoginBean.DataBean.UserinfoBean userinfo;
    @SuppressLint("SetTextI18n")
    private void initData(LoginBean.DataBean.UserinfoBean userinfo) {
        if (userinfo != null) {
            if (!TextUtils.isEmpty(userinfo.getAvatar())) {
                LoadImageUtil.loadImg(userinfo.getAvatar(), iv_headimg, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            }

            tv_vip_lv.setText(String.format(getActivity().getResources().getString(R.string.my_frag_viplv), String.valueOf(userinfo.getLevel())));
            if (0 == userinfo.getLevel()) {
                my_p_grade_img.setImageResource(R.drawable.icon_rumen);
            } else if (1 == userinfo.getLevel()) {
                my_p_grade_img.setImageResource(R.drawable.icon_rumen);
            } else if (2 == userinfo.getLevel()) {
                my_p_grade_img.setImageResource(R.drawable.icon_rumen);
            } else if (3 == userinfo.getLevel()) {
                my_p_grade_img.setImageResource(R.drawable.icon_rumen);
            } else {
                my_p_grade_img.setImageResource(R.drawable.icon_rumen);
            }

            if ("男".equals(userinfo.getSex())) {
                iv_sex.setImageResource(R.drawable.icon_nan);
            } else if ("女".equals(userinfo.getSex())) {
                iv_sex.setImageResource(R.drawable.icon_nv);
            }
            if (!TextUtils.isEmpty(userinfo.getMobile()) && userinfo.getMobile().length() >= 11) {
                String mobile = userinfo.getMobile();
                String start = mobile.substring(0, 4);
                String end = mobile.substring(7);
                String newMobile = start + "****" + end;
                tv_phone.setText(newMobile);
            }
            switch (userinfo.getVIP()){
                case 0:
                    tv_less_count.setText("赶紧开通vip吧");
                    break;
                case 1:
                    tv_less_count.setText("尊敬的vip1");
                    break;
                case 2:
                    tv_less_count.setText("尊敬的vip2");
                    break;
                case 3:
                    tv_less_count.setText("尊敬的vip代理");
                    break;
            }
            tv_look_count.setText(String.valueOf(userinfo.getMoney()));
        }
    }

    private void getPerInfo(final String uid) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginBll loginBll = new LoginBll(mContext);
                    loginBll.getPersonInfo(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread = null;
    }

    private void setListeners() {
        llyt_tuiguang.setOnClickListener(this);
        llyt_feedback.setOnClickListener(this);
        llyt_tongzhi.setOnClickListener(this);
        llyt_jiaoliuqun.setOnClickListener(this);

        rlyt_my_lishijilu.setOnClickListener(this);
        rlyt_my_huancun.setOnClickListener(this);
        rlyt_my_xihuan.setOnClickListener(this);
        iv_settiing.setOnClickListener(this);
        iv_headimg.setOnClickListener(this);
        tv_vip_duihuan.setOnClickListener(this);
        my_p_cishu.setOnClickListener(this);
        my_p_dengji.setOnClickListener(this);
        tv_vip_lv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llyt_tuiguang:
                gotoTargetActivity(TuiGuangActivity.class);
                break;
            case R.id.llyt_feedback:
                gotoTargetActivity(FeedBackActivity.class);
                break;
            case R.id.llyt_tongzhi:
                gotoTargetActivity(NoticeActivity.class);
                break;
            case R.id.llyt_jiaoliuqun:
                if (!TextUtils.isEmpty(url)) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mContext.startActivity(intent);
                    }catch (Exception e){
                    }
                } else {
                    Uri uri = Uri.parse("http://www.baidu.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
                break;
            case R.id.rlyt_my_lishijilu:
                gotoTargetActivity(LookHistoryActivity.class);
                break;
            case R.id.rlyt_my_huancun:
                gotoTargetActivity(DownListActivity.class);
                break;
            case R.id.rlyt_my_xihuan:
                gotoTargetActivity(MyLikesActivity.class);
                break;
            case R.id.iv_settiing:
                gotoTargetActivity(SettinsActivity.class);
                break;
            case R.id.iv_headimg:
                gotoTargetActivity(AccountManagerActivity.class);
                break;
            case R.id.tv_vip_duihuan:
                gotoTargetActivity(VipActivity.class);
//                gotoTargetActivity(MyhuiyuanActivity.class);
                break;
            case R.id.my_p_cishu:
                gotoTargetActivity(TuiGuangActivity.class);
                break;
            case R.id.my_p_dengji:
                gotoTargetActivity(TuiGuangActivity.class);
                break;
            case R.id.tv_vip_lv:
                gotoTargetActivity(TuiGuangActivity.class);
                break;
        }
    }

    private void startQQ() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://q.url.cn/s");
        intent.setData(uri);
        startActivity(intent);
    }

    // 观看记录
    private void initHistoryAdapter(List<LishiBean> data) {
        historyAdapter = new MyRecyHistoryAdapter(getActivity(), data, "0");
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_history.setLayoutManager(manager);
        rcv_history.setAdapter(historyAdapter);
    }

    // 我的喜欢
    private void initLikesAdapter(List<LikeBean> data) {
        myLikeAdapter = new MyRecyLikeAdapter(getActivity(), data, "2");
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_mylikes.setLayoutManager(manager);
        rcv_mylikes.setAdapter(myLikeAdapter);
    }

    // 离线缓存
    private void initDownAdapter(List<VideoDownDBModel> data) {
        myDownAdapter = new MyRecyDownAdapter(getActivity(), data);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_downlist.setLayoutManager(manager);
        rcv_downlist.setAdapter(myDownAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
