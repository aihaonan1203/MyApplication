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
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.bean.ReboBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RevyLikesAdapter2 extends RecyclerView.Adapter<RevyLikesAdapter2.ViewHolder> {

    private Context mContext;
    private List<AVBean.VideoBean.DataBean> dbModels;

    public RevyLikesAdapter2(Context mContext, List<AVBean.VideoBean.DataBean> dbModels) {
        this.mContext = mContext;
        setDbModels(dbModels);
    }


    public void setDbModels(List<AVBean.VideoBean.DataBean> dbModels) {
        if (dbModels == null) {
            dbModels = new ArrayList<>();
        }
        this.dbModels = dbModels;
    }

    @Override
    public RevyLikesAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_def_ulikes, parent, false);
        RevyLikesAdapter2.ViewHolder hol = new RevyLikesAdapter2.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AVBean.VideoBean.DataBean model = dbModels.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_like_icon, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            }
            holder.tv_item_like_filmName.setText(model.getName());
            holder.tv_item_score.setText(String.valueOf(model.getScore()));
            holder.llyt_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReboBean.DataBean dataBean=new ReboBean.DataBean(model.getId(),model.getName(),model.getImage(),model.getVideofile(),
                            model.getFanhao(),model.getActor(),model.getCategory_id(),model.getKeyword(),model.getSpecial(),model.getLabel(),
                            model.getViews(),model.getScore(),model.getLikes(),model.getContent(),model.getGoodnum(),model.getBadnum(),model.getCreate_time(), model.getUpdate_time());
                    Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                    EventBus.getDefault().postSticky(dataBean);
                    mContext.startActivity(intent);
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
