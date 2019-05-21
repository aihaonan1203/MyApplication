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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.bean.ReboBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RevyLikesAdapter extends RecyclerView.Adapter<RevyLikesAdapter.ViewHolder> {

    private Context mContext;
    private List<ReboBean.DataBean> dbModels;

    public RevyLikesAdapter(Context mContext, List<ReboBean.DataBean> dbModels) {
        this.mContext = mContext;
        setDbModels(dbModels);
    }


    public void setDbModels(List<ReboBean.DataBean> dbModels) {
        if (dbModels == null) {
            dbModels = new ArrayList<>();
        }
        this.dbModels = dbModels;
    }

    @Override
    public RevyLikesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_def_ulikes, parent, false);
        RevyLikesAdapter.ViewHolder hol = new RevyLikesAdapter.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReboBean.DataBean model = dbModels.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_like_icon, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            }
            holder.tv_item_like_filmName.setText(model.getName());
            holder.tv_item_score.setText(String.valueOf(model.getScore()));
            holder.llyt_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                    mContext.startActivity(intent);
                    EventBus.getDefault().postSticky(model);
                }
            });
        }
//        //计算每条的宽度
//        ViewGroup.LayoutParams layoutParams = holder.rllt.getLayoutParams();
//        layoutParams.width = screenWidth * 3 / 5;
//        holder.rllt.setLayoutParams(layoutParams);
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
            llyt_detail = itemView.findViewById(R.id.llyt_detail);
            tv_item_score = itemView.findViewById(R.id.tv_item_score);
        }

        private ImageView iv_item_like_icon;
        private TextView tv_item_like_filmName;
        private RelativeLayout llyt_detail;
        private TextView tv_item_score;
    }
}
