package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.AvacActivity;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.view.RoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class ZhuanlanLvAdapter extends BaseAdapter {

    private List<AVBean> data;
    private Context mContext;


    public ZhuanlanLvAdapter(Context context, List<AVBean> data) {
        mContext = context;
        setData(data);
    }

    public void setData(List<AVBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_channel_zl_pop_lv, null);
            holder.mItemZltjPopImg = convertView.findViewById(R.id.item_zltj_pop_img);
            holder.mItemZltjPopName = convertView.findViewById(R.id.item_zltj_pop_name);
            holder.mItemZltjPopMsg = convertView.findViewById(R.id.item_zltj_pop_msg);
            holder.mItemZltjPopHlv = convertView.findViewById(R.id.item_zltj_pop_hlv);
            holder.tv_item_filmnum = convertView.findViewById(R.id.tv_item_filmnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AVBean model = data.get(position);
        if (model != null) {
            LoadImageUtil.loadImg(model.getAvatar_image(), holder.mItemZltjPopImg, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            holder.mItemZltjPopName.setText(model.getActor());
            holder.mItemZltjPopMsg.setText(model.getIntro());
            holder.tv_item_filmnum.setText(model.getVideo().getTotal()+"部影片");
            List<AVBean.VideoBean.DataBean> videos = model.getVideo().getData();
            if (videos != null && videos.size() > 0) {
                holder.mItemZltjPopHlv.setVisibility(View.VISIBLE);
                RevyLikesAdapter2 adapter = new RevyLikesAdapter2(mContext, videos);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder.mItemZltjPopHlv.setLayoutManager(manager);
                holder.mItemZltjPopHlv.setAdapter(adapter);
            } else {
                holder.mItemZltjPopHlv.setVisibility(View.GONE);
            }

            holder.mItemZltjPopImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AvacActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(102,model));
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }


    private class ViewHolder {
        private RoundImageView mItemZltjPopImg;
        private TextView mItemZltjPopName;
        private TextView mItemZltjPopMsg;
        private RecyclerView mItemZltjPopHlv;
        private TextView tv_item_filmnum;
    }
}
