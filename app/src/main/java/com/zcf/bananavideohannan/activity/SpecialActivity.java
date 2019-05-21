package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.SpecialAdapter;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.domain.DefFilmDomain;
import com.zcf.bananavideohannan.evenMsg.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_special)
public class SpecialActivity extends MyBaseActivity {

    @ViewInject(R.id.iv_top_bg)
    private ImageView iv_top_bg;

    @ViewInject(R.id.tv_special_name)
    private TextView tv_special_name;

    @ViewInject(R.id.tv_special_content)
    private TextView tv_special_content;

    @ViewInject(R.id.gv_films)
    private GridView gv_films;

    private List<DefFilmDomain> data;

    private SpecialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        if (intent.getExtras() != null) {
//            Bundle bundle = intent.getExtras();
//            if (bundle.containsKey("special")) {
//                String specilId = bundle.getString("special").toString();
//                if (!TextUtils.isEmpty(specilId)) {
//                    loadData(specilId);
//                }
//            }
//        }
        if (model!=null){
            tv_special_name.setText(model.getName());
            tv_special_content.setText(model.getDescription());
            LoadImageUtil.loadImg(model.getImage(), iv_top_bg, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            initAdapter(model.getValue());
        }
        iv_top_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ZltjTjResultBean.DataBean model;
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void XX(EventMessage<ZltjTjResultBean.DataBean> message){
        this.model=message.getData();
    }

//    /**
//     * 获取数据
//     */
//    private void loadData(String special) {
//        ProgressDialogUtil.showDialog(mContext, "", true);
//        OkGo.<String>post(ACTION_URL_GET_ZT_DETAIL).tag(this).isMultipart(false)
//                .params("special", special)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            SpecialBean bean = GsonUtil.parseJsonWithGson(response.body(), SpecialBean.class);
//                            if (bean != null) {
//                                List<DefFilmDomain> data = bean.getData();
//                                if (data != null && data.size() > 0) {
//                                    initAdapter(data);
//                                }
//                                SpecialBean.Special model = bean.getSpecial();
//                                if (model != null) {
//                                    if (!TextUtils.isEmpty(model.getImage()))
//                                        LoadImageUtil.loadImg(model.getImage(), iv_top_bg, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
//                                    tv_special_name.setText(model.getSpecial());
//                                    tv_special_content.setText(model.getIntro());
//                                }
//                            }
//                        }
//                        ProgressDialogUtil.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        ProgressDialogUtil.dismiss();
//                        super.onError(response);
//                    }
//                });
//    }

    private void initAdapter(List<ZltjTjResultBean.DataBean.ValueBean> value) {
        adapter = new SpecialAdapter(mContext, value);
        gv_films.setAdapter(adapter);
    }

}
