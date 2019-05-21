package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.NoticeDetailActivity;
import com.zcf.bananavideohannan.bean.NoticeBean;
import com.zcf.bananavideohannan.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends BaseAdapter {
    private Context mContext;
    private List<NoticeBean.NoticeDomain> data;

    public NoticeAdapter(Context mContext, List<NoticeBean.NoticeDomain> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_notice, null);
            holder.llyt_item = view.findViewById(R.id.llyt_item);
            holder.tv_date = view.findViewById(R.id.tv_date);
            holder.tv_content = view.findViewById(R.id.tv_content);
            holder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        NoticeBean.NoticeDomain model = data.get(i);
        if (model != null) {
            holder.tv_title.setText(model.getTitle());
            holder.tv_content.setText(model.getContent());
            holder.tv_date.setText(Utils.getStrDate(model.getCreate_time()));
        }

        holder.llyt_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, NoticeDetailActivity.class));
            }
        });
        return view;
    }

    public void setData(List<NoticeBean.NoticeDomain> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    class ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_content;
        private LinearLayout llyt_item;

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
