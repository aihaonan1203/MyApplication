package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AVactorFilmAdapter extends BaseAdapter {
    private Context mContext;
    private List<AVBean.VideoBean.DataBean> data;

    public AVactorFilmAdapter(Context mContext, List<AVBean.VideoBean.DataBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_actor_detail, null);
            holder.iv_item_icon = view.findViewById(R.id.iv_item_icon);
            holder.tv_item_score = view.findViewById(R.id.tv_item_score);
            holder.tv_item_filmName = view.findViewById(R.id.tv_item_filmName);
            holder.tv_item_playNum = view.findViewById(R.id.tv_item_playNum);
            holder.llyt_item_film = view.findViewById(R.id.llyt_item_film);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final AVBean.VideoBean.DataBean model = data.get(i);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_icon, LoadImageUtil.getImageOptions(R.drawable.testimg1), R.drawable.testimg1);
            }

            holder.tv_item_score.setText(String.valueOf(model.getScore()));
            holder.tv_item_filmName.setText(model.getName());
            holder.tv_item_playNum.setText(Utils.getStrPlayNum(model.getViews()));

            holder.llyt_item_film.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PlayVideoDetailActivity.EXTRA_VIDEROURL, model.getVideofile());
                    bundle.putString(PlayVideoDetailActivity.EXTRA_ICONURL, model.getImage());
                    bundle.putString(PlayVideoDetailActivity.EXTRA_TITLE, model.getName());
                    bundle.putInt(PlayVideoDetailActivity.EXTRA_VIDEO_ID, model.getId());
                    Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

    public void setData(List<AVBean.VideoBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    class ViewHolder {
        private ImageView iv_item_icon;
        private TextView tv_item_score;
        private TextView tv_item_filmName;
        private TextView tv_item_playNum;
        private LinearLayout llyt_item_film;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
