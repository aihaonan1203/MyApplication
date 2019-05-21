package com.zcf.bananavideohannan.activity.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.OfflineAdapter;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bll.DownBll;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;
import com.zcf.bananavideohannan.util.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

@ContentView(R.layout.activity_down_list)
public class DownListActivity extends MyBaseActivity {
    @ViewInject(R.id.lv_mylikes)
    private ListView lv_mylikes;

    @ViewInject(R.id.llyt_bottom)
    private LinearLayout llyt_bottom;

    @ViewInject(R.id.tv_edit)
    private TextView tv_edit;

    @ViewInject(R.id.tv_select_all)
    private TextView tv_select_all;

    private OfflineAdapter adapter;
    private List<VideoDownDBModel> data;
    private boolean isEditStatus;

    public static final String EXTRA_VIDEO_ID = "videoid";
    public static final String EXTRA_VIDEO_URL = "videourl";
    public static final String EXTRA_VIDEO_PROGRESS = "progress";
    public static final int HANDLER_NOTICE_DOWN_PROGRSS = 1;
    public static Handler handler;
    private boolean isAllSelect;

    public void setData(List<VideoDownDBModel> data) {
        this.data = data;
    }

    private String uid;
    private DownBll downBll;
    private String downing_id;
    private int progrss;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downBll = new DownBll(mContext);

        if (app != null && app.getParam(USER_UID) != null) {
            uid = app.getParam(USER_UID).toString();
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HANDLER_NOTICE_DOWN_PROGRSS) {
                    Bundle bundle = msg.getData();
                    updateProgress(bundle);
                } else if (msg.what == 2) {
                    getData();
                }
            }
        };

        getData();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        updateProgress(bundle);
    }

    private void updateProgress(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_VIDEO_ID)) {
                downing_id = bundle.getString(EXTRA_VIDEO_ID);
            }
            if (bundle.containsKey(EXTRA_VIDEO_ID)) {
                downing_id = bundle.getString(EXTRA_VIDEO_ID);
            }

            if (bundle.containsKey(EXTRA_VIDEO_PROGRESS)) {
                progrss = bundle.getInt(EXTRA_VIDEO_PROGRESS);
            }

            if (!listIsNull(data)) {
                for (int i = 0; i < data.size(); i++) {
                    VideoDownDBModel model = data.get(i);
                    if (downing_id.equals(String.valueOf(model.getVideo_id()))) {
                        if (progrss == 100) {
                            model.setDowning(false);
                        } else {
                            model.setDowning(true);
                        }
                        model.setProgress(progrss);
                        data.set(i, model);
                    }
                }
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }
        }
    }


    private void getData() {
        data = downBll.getList();
        adapter = new OfflineAdapter(mContext, data);
        lv_mylikes.setAdapter(adapter);
    }

    public static Vector<String> getFileName(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(VIDEO_CACHE_DIR);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                Log.e("eee", "文件名 ： " + filename);
            }
        }
        return vecFile;
    }

    @Event(R.id.tv_select_del)
    private void delete(View view) {
        HashMap<String, String> mpdata_select = adapter.getMpdata_select();
        if (mpdata_select == null || mpdata_select.size() <= 0) {
            ToastUtil.toastShort(mContext, "请先选择要删除的");
            return;
        }

        ToastUtils.show(mContext, "删除中...");

        for (String key : mpdata_select.keySet()) {
            downBll.deleteById(key);
            VideoDownDBModel model = downBll.getByVideoId(key);
            if (model != null) {
                String filepath = model.getFilePath();
                if (!TextUtils.isEmpty(filepath)) {
                    File file = new File(filepath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }

        data = downBll.getList();
        adapter.setData(data);
        adapter.notifyDataSetChanged();

        ToastUtils.dismiss();
    }

    @Event(R.id.tv_edit)
    private void changeShowStatus(View view) {
        if (llyt_bottom.getVisibility() == View.GONE) {
            if (data.size()==0){
                Toast.makeText(mContext, "当前没有影片！", Toast.LENGTH_SHORT).show();
                return;
            }
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
                adapter.notifyDataSetChanged();
            }
            isAllSelect = true;
        } else {
            tv_select_all.setText("全选");
            isAllSelect = false;
            adapter.setMpdata_select(new HashMap<String, String>());
            adapter.notifyDataSetChanged();
        }
    }
}
