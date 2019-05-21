package com.zcf.bananavideohannan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.network.Network;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SpecialListActivity extends MyBaseActivity {

    private ImageView ivBack;
    private GridView mvSpecialList;
    private List<ZltjTjResultBean.DataBean> data;
    private Map<Integer, List<ZltjTjResultBean.DataBean>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_list);
        initView();
        initListener();
        getZtData();
    }

    private void getZtData() {
        Disposable d = Network.getVideoApi().special_index("hot", "1")
                .compose(this.<ZltjTjResultBean>applySchedulers())
                .subscribe(new Consumer<ZltjTjResultBean>() {
                    @Override
                    public void accept(ZltjTjResultBean resultBean) throws Exception {
                        Log.e("accept: ", "");
                        data = resultBean.getData();
                        mvSpecialList.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return data.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                ViewHolder holder = null;
                                if (convertView == null) {
                                    holder = new ViewHolder();
                                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_channel_zl_gridview, null);
                                    holder.tv_item_zl_title = convertView.findViewById(R.id.tv_item_zl_title);
                                    holder.iv_item_zl_img = convertView.findViewById(R.id.iv_item_zl_img);
                                    holder.llyt_nvdetail = convertView.findViewById(R.id.llyt_nvdetail);
                                    convertView.setTag(holder);
                                } else {
                                    holder = (ViewHolder) convertView.getTag();
                                }

                                final ZltjTjResultBean.DataBean model = data.get(position);
                                if (model != null) {
                                    holder.tv_item_zl_title.setText(model.getName());
                                    if (!TextUtils.isEmpty(model.getImage())) {
                                        LoadImageUtil.loadImg(model.getImage(), holder.iv_item_zl_img, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
                                    } else {
                                        holder.iv_item_zl_img.setImageResource(R.drawable.moren_headimg);
                                    }

                                    holder.llyt_nvdetail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(mContext, SpecialActivity.class);
                                            EventBus.getDefault().postSticky(new EventMessage<>(101, model));
                                            mContext.startActivity(intent);
                                        }
                                    });
                                }
                                return convertView;
                            }
                        });

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ", "");
                    }
                });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        mvSpecialList = findViewById(R.id.mv_special_list);
    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class ViewHolder {
        private TextView tv_item_zl_title;
        private ImageView iv_item_zl_img;
        private LinearLayout llyt_nvdetail;
    }
}
