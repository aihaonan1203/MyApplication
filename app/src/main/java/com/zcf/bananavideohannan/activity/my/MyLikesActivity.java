package com.zcf.bananavideohannan.activity.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.PlayHistoryAdapter;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LikeBean;
import com.zcf.bananavideohannan.bean.OfflineCacheBean.OffCacheBean;
import com.zcf.bananavideohannan.evenMsg.LikeEvent;
import com.zcf.bananavideohannan.network.Network;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

@ContentView(R.layout.activity_my_likes)
public class MyLikesActivity extends MyBaseActivity {
    @ViewInject(R.id.lv_mylikes)
    private ListView lv_mylikes;

    @ViewInject(R.id.llyt_bottom)
    private LinearLayout llyt_bottom;

    @ViewInject(R.id.tv_select_all)
    private TextView tv_select_all;

    @ViewInject(R.id.tv_edit)
    private TextView tv_edit;

    private PlayHistoryAdapter adapter;
    private List<OffCacheBean.DataBeanX.DataBean> data;
    private boolean isEditStatus;

    public void setData(List<OffCacheBean.DataBeanX.DataBean> data) {
        this.data = data;
    }

    private BvAndroidContent app;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (BvAndroidContent) mContext.getApplicationContext();
        initAdapter();
    }

    @Event(R.id.tv_select_del)
    private void delete(View view) {
        HashMap<String, String> mpdata_select = adapter.getMpdata_select();
        if (mpdata_select == null || mpdata_select.size() <= 0) {
            ToastUtil.toastShort(mContext, "请先选择要删除的");
            return;
        }

        StringBuffer sbstr = new StringBuffer();
        for (String key : mpdata_select.keySet()) {
            sbstr.append(key).append(",");
        }

        String strDele = "";
        if (!TextUtils.isEmpty(sbstr.toString())) {
            strDele = sbstr.substring(0, sbstr.length() - 1);
        }

        ProgressDialogUtil.showDialog(mContext, "删除中...", true);
        Disposable D = Network.getVideoApi().dellike(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(), strDele)
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        ProgressDialogUtil.dismiss();
                        JSONObject json = new JSONObject(responseBody.string());
                        if (json.getInt("code") == 200) {
                            Toast.makeText(mContext, "删除成功!", Toast.LENGTH_LONG).show();
                            getMyLikesData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(mContext, "删除失败!", Toast.LENGTH_LONG).show();
                        ProgressDialogUtil.dismiss();
                    }
                });
//        OkGo.<String>post(ACTION_URL_DEL_VIDEOS_LIKE).tag(this)
//                .params("uid", uid)
//                .params("video_id", strDele)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            JsonResultObject bean = GsonUtil.parseJsonWithGson(response.body(), JsonResultObject.class);
//                            if (bean != null) {
//                                if (RESULT_CODE_SUCCESS == bean.getCode()) {
//                                    ToastUtil.toastShort(mContext, "删除成功");
//
//                                    HashMap<String, String> mpdata_select = adapter.getMpdata_select();
//                                    List<OffCacheBean.DataBeanX.DataBean> videos = app.getHistoryData().get(BvAndroidContent.APP_LIKES);
//                                    if (mpdata_select != null && mpdata_select.size() > 0 && videos != null && videos.size() > 0) {
//                                        for (String key : mpdata_select.keySet()) {
//                                            String video_id = key;
//                                            for (int i = 0; i < videos.size(); i++) {
//                                                OffCacheBean.DataBeanX.DataBean video = videos.get(i);
//                                                if (video_id.equals(String.valueOf(video.getVideo_id()))) {
//                                                    videos.remove(i);
//                                                }
//                                            }
//                                        }
//                                        data = videos;
//                                    }
//                                    adapter.setData(data);
//                                    adapter.notifyDataSetChanged();
//
//                                } else {
//                                    ToastUtil.toastShort(mContext, "删除失败");
//                                }
//                            }
//                        }
//                        ProgressDialogUtil.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        ProgressDialogUtil.dismiss();
//                        ToastUtil.toastShort(mContext, "删除失败");
//                        super.onError(response);
//                    }
//                });
    }

    private void getMyLikesData() {
        Disposable d = Network.getVideoApi().getMyLike(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString())
                .compose(this.<CommonlyBean<LikeBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<LikeBean>>() {
                    @Override
                    public void accept(CommonlyBean<LikeBean> likeBeanCommonlyBean) throws Exception {
                        List<LikeBean> data = likeBeanCommonlyBean.getData();
                        likeBeans = data;
                        initAdapter();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    List<LikeBean> likeBeans;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(LikeEvent likeEvent) {
        this.likeBeans = likeEvent.getLikeBeans();
    }

    private void initAdapter() {
        if (likeBeans == null) {
            likeBeans = new ArrayList<>();
        }
        adapter = new PlayHistoryAdapter(mContext, likeBeans);
        lv_mylikes.setAdapter(adapter);
    }

    @Event(R.id.tv_edit)
    private void changeShowStatus(View view) {
        if (llyt_bottom.getVisibility() == View.GONE) {
            llyt_bottom.setVisibility(View.VISIBLE);
            tv_edit.setText("取消");
            isEditStatus = true;
        } else {
            llyt_bottom.setVisibility(View.GONE);
            tv_edit.setText("编辑");
            isEditStatus = false;
        }

        if (adapter != null) {
            adapter.setShowCheck(isEditStatus);
            adapter.notifyDataSetChanged();
        }
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    boolean isAllSelect = false;

    @Event(R.id.tv_select_all)
    private void selectall(View view) {
        if (!isAllSelect) {
            tv_select_all.setText("取消");
            HashMap<String, String> mpdata = new HashMap<>();
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    mpdata.put(String.valueOf(data.get(i).getVideo_id()), "");
                }
                adapter.setMpdata_select(mpdata);
                app.setMp_selected(adapter.getMpdata_select());
                adapter.notifyDataSetChanged();
            }
            isAllSelect = true;
        } else {
            tv_select_all.setText("全选");
            adapter.setMpdata_select(new HashMap<String, String>());
            app.setMp_selected(adapter.getMpdata_select());
            adapter.notifyDataSetChanged();
            isAllSelect = false;
        }
    }
}
