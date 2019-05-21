package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.AvacActivity;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.view.XCRoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ActorAdapter extends BaseAdapter {
    private Context mContext;
    private List<AVBean> data;

    public ActorAdapter(Context mContext, List<AVBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_avtor_list, null);
            holder.llyt_actor = view.findViewById(R.id.llyt_actor);
            holder.iv_av_head = view.findViewById(R.id.iv_av_head);
            holder.iv_av_name = view.findViewById(R.id.iv_av_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final AVBean model = data.get(i);
        if (model != null) {
            LoadImageUtil.loadImg(model.getAvatar_image(), holder.iv_av_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            holder.iv_av_name.setText(model.getActor());
            holder.llyt_actor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AvacActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(102,model));
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
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

    class ViewHolder {
        LinearLayout llyt_actor;
        XCRoundImageView iv_av_head;
        TextView iv_av_name;
    }
}
