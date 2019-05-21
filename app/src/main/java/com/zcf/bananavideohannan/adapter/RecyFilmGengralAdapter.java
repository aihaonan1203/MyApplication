package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.domain.BqSearchDomain;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.AutoLinefeedLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyFilmGengralAdapter extends RecyclerView.Adapter<RecyFilmGengralAdapter.ViewHolder> {
    private Context mContext;
    private List<BqSearchDomain> data;

    public RecyFilmGengralAdapter(Context mContext, List<BqSearchDomain> data) {
        this.mContext = mContext;
        setData(data);
    }

    public void setData(List<BqSearchDomain> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_film_detail, parent, false);
        ViewHolder hol = new ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BqSearchDomain model = data.get(position);
        if (model != null) {
            if (model.getLabel() != null) {
                String[] labels = model.getLabel().split(",");
                for (int j = 0; j < labels.length; j++) {
                    final String label = labels[j];
                    TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.childview_film_label, null);
                    textView.setText(label);
                    holder.ll_parent.addView(textView);
                }
            }

            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_icon, LoadImageUtil.getImageOptions(R.drawable.white), R.drawable.white);
            }

            holder.tv_item_score.setText(model.getScore());
            holder.tv_item_filmName.setText(model.getName());
            holder.tv_item_playNum.setText(Utils.getStrPlayNum(model.getViews()));
            holder.llyt_item_film.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PlayVideoDetailActivity.EXTRA_VIDEROURL, model.getVideo());
                    bundle.putString(PlayVideoDetailActivity.EXTRA_ICONURL, model.getImage());
                    bundle.putString(PlayVideoDetailActivity.EXTRA_TITLE, model.getName());
                    bundle.putInt(PlayVideoDetailActivity.EXTRA_VIDEO_ID, model.getVideo_id());
                    Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            ll_parent = view.findViewById(R.id.ll_parent);
            iv_item_icon = view.findViewById(R.id.iv_item_icon);
            tv_item_score = view.findViewById(R.id.tv_item_score);
            tv_item_filmName = view.findViewById(R.id.tv_item_filmName);
            tv_item_playNum = view.findViewById(R.id.tv_item_playNum);
            llyt_item_film = view.findViewById(R.id.llyt_item_film);
        }

        private AutoLinefeedLayout ll_parent;
        private ImageView iv_item_icon;
        private TextView tv_item_score;
        private TextView tv_item_filmName;
        private TextView tv_item_playNum;
        private LinearLayout llyt_item_film;
    }

}
