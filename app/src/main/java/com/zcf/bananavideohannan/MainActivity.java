package com.zcf.bananavideohannan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gaotai.baselib.log.LogFile;
import com.gaotai.baselib.util.CompleteQuit;
import com.gaotai.baselib.util.FileUtils;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.activity.login.LoginActivity;
import com.zcf.bananavideohannan.adapter.MainFragmentPagerAdapter;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.GongGaoBean;
import com.zcf.bananavideohannan.bean.SearchTypeBean;
import com.zcf.bananavideohannan.fragment.ChannelFragment;
import com.zcf.bananavideohannan.fragment.FindFragment;
import com.zcf.bananavideohannan.fragment.MainDefFragment;
import com.zcf.bananavideohannan.fragment.MyFragment;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.ViewPagerNoScroll;
import com.zcf.bananavideohannan.view.dialog.DialogDefGonggao;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends MyBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.main_viewpager)
    private ViewPagerNoScroll main_viewpager;

    @ViewInject(R.id.rg_main_tabs)
    private RadioGroup rg_main_tabs;

    @ViewInject(R.id.rbtn_main_tab_def)
    private RadioButton rbtn_main_tab_def;

    @ViewInject(R.id.rbtn_main_tab_channel)
    private RadioButton rbtn_main_tab_channel;

    @ViewInject(R.id.rbtn_main_tab_find)
    private RadioButton rbtn_main_tab_find;

    @ViewInject(R.id.rbtn_main_tab_my)
    private RadioButton rbtn_main_tab_my;

    private List<Fragment> fragments;
    private MainFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app != null) {
            app.setParam(USER_ISREFRESH_PERSONINFO, USER_FRESH_YES);
            if (app.getParam(Consts.USER_UID) != null) {

            }
        }
        if (!checkLogin()) {
            try {
                File file = new File(TXT_LOOK_LESS_NUM);
                if (file.exists() && file.isFile()) {
                    String looknum = FileUtils.readAllContent(file);
                    if (!TextUtils.isEmpty(looknum)) {
                        app.setParam(Consts.USER_PLAYNUM, looknum);
                    }
                } else {
                    LogFile.writeLogToFile("5", TXT_LOOK_LESS_NUM);
                    app.setParam(Consts.USER_PLAYNUM, "5");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initFragments();
        initAdapter();
        setListeners();
        getHotSearch();
        getGonggao();
    }

    private void getHotSearch() {
        OkGo.<String>post(ACTION_URL_SEARCH_HOT).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            SearchTypeBean bean = GsonUtil.parseJsonWithGson(response.body(), SearchTypeBean.class);
                            if (bean != null) {
                                List<SearchTypeBean.SearchType> data = bean.getData();
                                if (!listIsNull(data)) {
                                    app.setParam("search_type_data", data);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void getGonggao() {
        OkGo.<String>post(ACTION_URL_GET_GONGGAO).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            GongGaoBean bean = GsonUtil.parseJsonWithGson(response.body(), GongGaoBean.class);
                            if (bean != null) {
                                List<GongGaoBean.DataBean> data = bean.getData();
                                if (data != null) {
                                    showGonggDialog(data.get(0));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    DialogDefGonggao defGonggao;

    /**
     * 弹出公告
     */
    private void showGonggDialog(GongGaoBean.DataBean data) {
        defGonggao = new DialogDefGonggao(mContext, data);
        defGonggao.setCanceledOnTouchOutside(false);
        defGonggao.setView(new EditText(mContext));
        defGonggao.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = defGonggao.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        defGonggao.getWindow().setAttributes(lp);
    }

    /**
     * 验证是否登录过
     */
    private void isLogin1() {
        String uid = ContextProperties.readRemember(mContext, ContextProperties.REM_UID);
        String password = ContextProperties.readRemember(mContext, ContextProperties.REM_PASSWORD);
        if (TextUtils.isEmpty(uid) && TextUtils.isEmpty(password)) {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
            return;
        } else {
            ContextProperties.reLoadLogin(mContext);
        }
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new MainDefFragment());
        fragments.add(new ChannelFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());
    }

    /**
     * 图片集展示
     */
    private void initAdapter() {
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), main_viewpager, fragments);
        main_viewpager.setAdapter(adapter);
        adapter.setOnExtraPageChangeListener(new MainFragmentPagerAdapter.OnExtraPageChangeListener() {
            @Override
            public void onExtraPageSelected(int i) {
                switch (i) {
                    case 0:
                        rbtn_main_tab_def.setChecked(true);
                        break;
                    case 1:
                        rbtn_main_tab_channel.setChecked(true);
                        break;
                    case 2:
                        rbtn_main_tab_find.setChecked(true);
                        break;
                    case 3:
                        rbtn_main_tab_my.setChecked(true);
                        break;
                }
            }
        });
    }

    private void setListeners() {
        rbtn_main_tab_def.setOnClickListener(this);
        rbtn_main_tab_channel.setOnClickListener(this);
        rbtn_main_tab_find.setOnClickListener(this);
        rbtn_main_tab_my.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isLogined){
            gotoTargetActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.rbtn_main_tab_def:
                main_viewpager.setCurrentItem(0);
                break;
            case R.id.rbtn_main_tab_channel:
                main_viewpager.setCurrentItem(1);
                break;
            case R.id.rbtn_main_tab_find:
                main_viewpager.setCurrentItem(2);
                break;
            case R.id.rbtn_main_tab_my:
                main_viewpager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_SEARCH || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            ToastUtil.toastShort(mContext, "执行搜索");
        }
        return super.dispatchKeyEvent(event);
    }

    private long lastTime = 0;
    private final long intervalTime = 1000 * 2;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime < intervalTime) {
                CompleteQuit.getInstance().exit();
            } else {
                lastTime = currentTime;
                ToastUtil.toastShort(mContext, "再按一次退出程序");
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
