package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.AVactorFilmAdapter;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.bean.AvactorBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.util.GsonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ContentView(R.layout.activity_avac)
public class AvacActivity extends MyBaseActivity {

    @ViewInject(R.id.lv_filems)
    private ListView lv_filems;

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;

    @ViewInject(R.id.tv_filmnum)
    private TextView tv_filmnum;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.tv_shengap)
    private TextView tv_shengap;

    @ViewInject(R.id.tv_sanwei)
    private TextView tv_sanwei;

    @ViewInject(R.id.tv_zb)
    private TextView tv_zb;

    @ViewInject(R.id.tv_content)
    private TextView tv_content;

    @ViewInject(R.id.rb_remen)
    private RadioButton rb_remen;

    @ViewInject(R.id.rb_zuixin)
    private RadioButton rb_zuixin;

    @ViewInject(R.id.rg_video_type)
    private RadioGroup rg_video_type;

    private String actor;
    private String order = "views";

    AVactorFilmAdapter adapter;
    private List<AVBean.VideoBean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (bean != null) {
            LoadImageUtil.loadImg(bean.getAvatar_image(), iv_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            tv_filmnum.setText("" + bean.getVideo().getTotal() + "部影片");
            tv_name.setText(bean.getActor());
            tv_zb.setText("罩杯：" + bean.getCup());
            tv_content.setText(bean.getIntro());
            data = bean.getVideo().getData();
            if (!listIsNull(data)) {
                sort(2);
                initAdapter(data);
            }
        }
        rg_video_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case 0:
                        rb_remen.setChecked(true);
                        break;
                    case 1:
                        rb_zuixin.setChecked(true);
                        break;
                }
            }
        });

        rb_remen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData(actor, "likes");
                sort(2);
            }
        });

        rb_zuixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData(actor, "views");
                sort(1);
            }
        });
    }


    private void sort(final int type){
        Collections.sort(data, new Comparator<AVBean.VideoBean.DataBean>() {
            @Override
            public int compare(AVBean.VideoBean.DataBean o1, AVBean.VideoBean.DataBean o2) {
                if (type==1){
                    return o1.getCreate_time()-o2.getCreate_time();
                }
                return o2.getViews()-o1.getViews();
            }
        });
        initAdapter(data);
    }

    private AVBean bean;
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void XX(EventMessage<AVBean> message){
        this.bean=message.getData();
    }

    private void getData(String actor, String order) {
        ProgressDialogUtil.showDialog(mContext, "加载中...", true);
        OkGo.<String>post(ACTION_URL_GET_NY_DETAIL).tag(this)
                .params("actor", actor)
                .params("order", order)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            AvactorBean bean = GsonUtil.parseJsonWithGson(response.body(), AvactorBean.class);
                            if (bean != null) {
                                AvactorBean.Data data = bean.getData();
                                AvactorBean.Video video = bean.getVideo();
                                List<AvactorBean.Video.Data> list = video.getData();
                                if (data != null) {
                                    LoadImageUtil.loadImg(data.getAvatar(), iv_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
                                    tv_filmnum.setText("" + data.getPianliang() + "部影片");
                                    tv_name.setText(data.getActor());
                                    tv_zb.setText("罩杯：" + data.getCup());
                                    tv_content.setText(data.getIntro());
                                }


                            }
                        }
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressDialogUtil.dismiss();
                        super.onError(response);
                    }
                });
    }

    private void initAdapter(List<AVBean.VideoBean.DataBean> list) {
        if (adapter==null){
            adapter = new AVactorFilmAdapter(mContext, list);
            lv_filems.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }
}
