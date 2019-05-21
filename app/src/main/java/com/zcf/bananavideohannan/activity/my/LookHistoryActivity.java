package com.zcf.bananavideohannan.activity.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.MainFragmentPagerAdapter;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LishiBean;
import com.zcf.bananavideohannan.fragment.history.PlayHistpryMoreFragment;
import com.zcf.bananavideohannan.fragment.history.PlayHistprySevenFragment;
import com.zcf.bananavideohannan.fragment.history.PlayHistpryTodayFragment;
import com.zcf.bananavideohannan.network.Network;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 观看历史
 */
@ContentView(R.layout.activity_look_history)
public class LookHistoryActivity extends MyBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.viewpager_ph)
    private ViewPager viewpager_ph;

    @ViewInject(R.id.llyt_bottom)
    private LinearLayout llyt_bottom;

    @ViewInject(R.id.tv_select_all)
    private TextView tv_select_all;

    @ViewInject(R.id.tv_select_del)
    private TextView tv_select_del;

    @ViewInject(R.id.rg_main_tabs)
    private RadioGroup rg_main_tabs;

    @ViewInject(R.id.rbtn_ph_today)
    private RadioButton rbtn_ph_today;

    @ViewInject(R.id.rbtn_ph_sevday)
    private RadioButton rbtn_ph_sevday;

    @ViewInject(R.id.rbtn_ph_more)
    private RadioButton rbtn_ph_more;

    @ViewInject(R.id.tv_edit)
    private TextView tv_edit;

    private MainFragmentPagerAdapter fragmentAdapter;
    private String uid;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (app != null) {
            if (app.getParam(Consts.USER_UID) != null) {
                uid = app.getParam(Consts.USER_UID).toString();
            }
        }
        ProgressDialogUtil.showDialog(mContext, "加载中...", false);
        getData();
        setListeners();
    }

    public List<LishiBean> list1, list2, list3;

    private void getData() {
        Disposable d = Network.getVideoApi().getFootprint(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(), "1")
                .flatMap(new Function<CommonlyBean<LishiBean>, ObservableSource<CommonlyBean<LishiBean>>>() {
                    @Override
                    public ObservableSource<CommonlyBean<LishiBean>> apply(CommonlyBean<LishiBean> lishiBeanCommonlyBean) throws Exception {
                        list1 = lishiBeanCommonlyBean.getData();
                        return Network.getVideoApi().getFootprint(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(), "2");
                    }
                }).flatMap(new Function<CommonlyBean<LishiBean>, ObservableSource<CommonlyBean<LishiBean>>>() {
                    @Override
                    public ObservableSource<CommonlyBean<LishiBean>> apply(CommonlyBean<LishiBean> lishiBeanCommonlyBean) throws Exception {
                        list2 = lishiBeanCommonlyBean.getData();
                        return Network.getVideoApi().getFootprint(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(), "3");
                    }
                }).compose(this.<CommonlyBean<LishiBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<LishiBean>>() {
                    @Override
                    public void accept(CommonlyBean<LishiBean> lishiBeanCommonlyBean) throws Exception {
                        ProgressDialogUtil.dismiss();
                        list3 = lishiBeanCommonlyBean.getData();
                        initFragment();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private long svenTime = 7 * 24 * 60 * 60 * 1000;
    private long oneTime = 24 * 60 * 60 * 1000;


    private void initFragment() {
        rbtn_ph_today.setChecked(true);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PlayHistpryTodayFragment(list1));
        fragments.add(new PlayHistprySevenFragment(list2));
        fragments.add(new PlayHistpryMoreFragment(list3));
        fragmentAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), viewpager_ph, fragments);
        viewpager_ph.setAdapter(fragmentAdapter);
        fragmentAdapter.setOnExtraPageChangeListener(new MainFragmentPagerAdapter.OnExtraPageChangeListener() {
            @Override
            public void onExtraPageSelected(int i) {
                switch (i) {
                    case 0:
                        rbtn_ph_today.setChecked(true);
                        break;
                    case 1:
                        rbtn_ph_sevday.setChecked(true);
                        break;
                    case 2:
                        rbtn_ph_more.setChecked(true);
                        break;
                }

                if (llyt_bottom.getVisibility() == View.VISIBLE) {
                    llyt_bottom.setVisibility(View.GONE);
                    tv_edit.setText("编辑");
                    isEditStatus = false;
                }

                if (viewpager_ph.getCurrentItem() == 0) {
                    if (PlayHistpryTodayFragment.handler != null) {
                        PlayHistpryTodayFragment.handler.sendEmptyMessage(PlayHistpryTodayFragment.HANDLER_HIDE_CHECKBOX);
                    }
                } else if (viewpager_ph.getCurrentItem() == 1) {
                    if (PlayHistprySevenFragment.handler != null) {
                        PlayHistprySevenFragment.handler.sendEmptyMessage(PlayHistprySevenFragment.HANDLER_HIDE_CHECKBOX);
                    }
                } else if (viewpager_ph.getCurrentItem() == 2) {
                    if (PlayHistpryMoreFragment.handler != null) {
                        PlayHistpryMoreFragment.handler.sendEmptyMessage(PlayHistpryMoreFragment.HANDLER_HIDE_CHECKBOX);
                    }
                }
            }
        });
    }

    private void setListeners() {
        tv_select_all.setOnClickListener(this);
        tv_select_del.setOnClickListener(this);
        rbtn_ph_today.setOnClickListener(this);
        rbtn_ph_sevday.setOnClickListener(this);
        rbtn_ph_more.setOnClickListener(this);
    }

    boolean isAllSelect = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_ph_today:
                viewpager_ph.setCurrentItem(0);
                break;
            case R.id.rbtn_ph_sevday:
                viewpager_ph.setCurrentItem(1);
                break;
            case R.id.rbtn_ph_more:
                viewpager_ph.setCurrentItem(2);
                break;
            case R.id.tv_select_all:
                if (viewpager_ph.getCurrentItem() == 0) {
                    if (PlayHistpryTodayFragment.handler != null) {
                        if (!isAllSelect) {
                            PlayHistpryTodayFragment.handler.sendEmptyMessage(PlayHistpryTodayFragment.HANDLER_SELECT_ALL_CHECKBOX);
                            tv_select_all.setText("取消");
                            isAllSelect = true;
                        } else {
                            PlayHistpryTodayFragment.handler.sendEmptyMessage(PlayHistpryTodayFragment.HANDLER_SCANCLE_ELECT_ALL_CHECKBOX);
                            tv_select_all.setText("全选");
                            isAllSelect = false;
                        }
                    }
                } else if (viewpager_ph.getCurrentItem() == 1) {
                    if (PlayHistprySevenFragment.handler != null) {
                        if (!isAllSelect) {
                            PlayHistprySevenFragment.handler.sendEmptyMessage(PlayHistprySevenFragment.HANDLER_SELECT_ALL_CHECKBOX);
                            tv_select_all.setText("取消");
                            isAllSelect = true;
                        } else {
                            PlayHistprySevenFragment.handler.sendEmptyMessage(PlayHistprySevenFragment.HANDLER_SCANCLE_ELECT_ALL_CHECKBOX);
                            tv_select_all.setText("全选");
                            isAllSelect = false;
                        }
                    }
                } else if (viewpager_ph.getCurrentItem() == 2) {
                    if (PlayHistpryMoreFragment.handler != null) {
                        if (!isAllSelect) {
                            PlayHistpryMoreFragment.handler.sendEmptyMessage(PlayHistpryMoreFragment.HANDLER_SELECT_ALL_CHECKBOX);
                            tv_select_all.setText("取消");
                            isAllSelect = true;
                        } else {
                            PlayHistpryMoreFragment.handler.sendEmptyMessage(PlayHistpryMoreFragment.HANDLER_SCANCLE_ELECT_ALL_CHECKBOX);
                            tv_select_all.setText("全选");
                            isAllSelect = false;
                        }
                    }
                }
                break;
            case R.id.tv_select_del:
                currentItem = viewpager_ph.getCurrentItem();
                StringBuffer delete=new StringBuffer();
                switch (currentItem){
                    case 0:
                        for (int i = 0; i < list1.size(); i++) {
                            if (list1.get(i).isIscheck()){
                                delete.append(list1.get(i).getFpid()).append(",");
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).isIscheck()){
                                delete.append(list2.get(i).getFpid()).append(",");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < list3.size(); i++) {
                            if (list3.get(i).isIscheck()){
                                delete.append(list3.get(i).getFpid()).append(",");
                            }
                        }
                        break;
                }

                if (delete.length()==0) {
                    ToastUtil.toastShort(mContext, "请先选中要删除的");
                    return;
                }
                delete(delete);
                break;
        }
    }

    private void delete(StringBuffer sbstr) {
            String strDele = "";
            if (!TextUtils.isEmpty(sbstr.toString())) {
                strDele = sbstr.substring(0, sbstr.length() - 1);
            }
            ProgressDialogUtil.showDialog(mContext, "删除中...", true);
            Disposable D=Network.getVideoApi().delfp(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(),strDele)
                    .compose(this.<ResponseBody>applySchedulers())
                    .subscribe(new Consumer<ResponseBody>() {
                        @Override
                        public void accept(ResponseBody responseBody) throws Exception {
                            JSONObject json=new JSONObject(responseBody.string());
                            if (json.getInt("code")==200) {
                                Toast.makeText(mContext, "删除成功!", Toast.LENGTH_LONG).show();
                                getData();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    boolean isEditStatus = false;// 是否为编辑状态下

    @Event(R.id.tv_edit)
    private void edit(View view) {
        // TODO: 2018/12/5  通知子页面显示checkbox
        changeShowStatus();
    }

    private void changeShowStatus() {
        if (llyt_bottom.getVisibility() == View.GONE) {
            llyt_bottom.setVisibility(View.VISIBLE);
            tv_edit.setText("取消");
            isEditStatus = true;
        } else {
            llyt_bottom.setVisibility(View.GONE);
            tv_edit.setText("编辑");
            isEditStatus = false;
        }

        if (viewpager_ph.getCurrentItem() == 0) {
            if (PlayHistpryTodayFragment.handler != null) {
                if (isEditStatus) {
                    PlayHistpryTodayFragment.handler.sendEmptyMessage(PlayHistpryTodayFragment.HANDLER_SHOW_CHECKBOX);
                } else {
                    PlayHistpryTodayFragment.handler.sendEmptyMessage(PlayHistpryTodayFragment.HANDLER_HIDE_CHECKBOX);
                }
            }
        } else if (viewpager_ph.getCurrentItem() == 1) {
            if (PlayHistprySevenFragment.handler != null) {
                if (isEditStatus) {
                    PlayHistprySevenFragment.handler.sendEmptyMessage(PlayHistprySevenFragment.HANDLER_SHOW_CHECKBOX);
                } else {
                    PlayHistprySevenFragment.handler.sendEmptyMessage(PlayHistprySevenFragment.HANDLER_HIDE_CHECKBOX);
                }
            }
        } else if (viewpager_ph.getCurrentItem() == 2) {
            if (PlayHistpryMoreFragment.handler != null) {
                if (isEditStatus) {
                    PlayHistpryMoreFragment.handler.sendEmptyMessage(PlayHistpryMoreFragment.HANDLER_SHOW_CHECKBOX);
                } else {
                    PlayHistpryMoreFragment.handler.sendEmptyMessage(PlayHistpryMoreFragment.HANDLER_HIDE_CHECKBOX);
                }
            }
        }
    }


}
