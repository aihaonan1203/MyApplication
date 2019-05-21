package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.my.LookHistoryActivity;
import com.zcf.bananavideohannan.activity.my.MyLikesActivity;
import com.zcf.bananavideohannan.bean.LishiBean;

import java.util.ArrayList;
import java.util.List;

public class MyRecyHistoryAdapter extends RecyclerView.Adapter<MyRecyHistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<LishiBean> dbModels;
    private String type;// 0-观看记录 1-离线缓存 2-我的喜欢

    public MyRecyHistoryAdapter(Context mContext, List<LishiBean> dbModels, String type) {
        this.mContext = mContext;
        setDbModels(dbModels);
        this.type = type;
    }


    public void setDbModels(List<LishiBean> dbModels) {
        if (dbModels == null) {
            dbModels = new ArrayList<>();
        }
        this.dbModels = dbModels;
    }

    @Override
    public MyRecyHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_recy, parent, false);
        MyRecyHistoryAdapter.ViewHolder hol = new MyRecyHistoryAdapter.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LishiBean model = dbModels.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_like_icon, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            }
            holder.tv_item_like_filmName.setText(model.getName());
            holder.llyt_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(type)) {
                        mContext.startActivity(new Intent(mContext, LookHistoryActivity.class));
                    } else if ("2".equals(type)) {
                        mContext.startActivity(new Intent(mContext, MyLikesActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dbModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            iv_item_like_icon = itemView.findViewById(R.id.iv_item_like_icon);
            tv_item_like_filmName = itemView.findViewById(R.id.tv_item_like_filmName);
            llyt_item = itemView.findViewById(R.id.llyt_item);
        }

        private ImageView iv_item_like_icon;
        private TextView tv_item_like_filmName;
        private LinearLayout llyt_item;
    }
}
