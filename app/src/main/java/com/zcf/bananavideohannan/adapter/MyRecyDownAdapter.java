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
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;

import java.util.ArrayList;
import java.util.List;

public class MyRecyDownAdapter extends RecyclerView.Adapter<MyRecyDownAdapter.ViewHolder> {

    private Context mContext;
    private List<VideoDownDBModel> dbModels;


    public MyRecyDownAdapter(Context mContext, List<VideoDownDBModel> dbModels) {
        this.mContext = mContext;
        setDbModels(dbModels);
    }


    public void setDbModels(List<VideoDownDBModel> dbModels) {
        if (dbModels == null) {
            dbModels = new ArrayList<>();
        }
        this.dbModels = dbModels;
    }

    @Override
    public MyRecyDownAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_recy, parent, false);
        MyRecyDownAdapter.ViewHolder hol = new MyRecyDownAdapter.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoDownDBModel model = dbModels.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_like_icon, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            }
            holder.tv_item_like_filmName.setText(model.getName());
            holder.llyt_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, DownListActivity.class));
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
