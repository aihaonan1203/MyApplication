package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.DcDateUtils;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.FeedbackResultBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    private Context mContext;
    private List<FeedbackResultBean.DataBean> data;

    public FeedbackAdapter(Context mContext, List<FeedbackResultBean.DataBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_feedback, null);
            holder.llyt_item = view.findViewById(R.id.llyt_item);
            holder.tv_item_fb_time = view.findViewById(R.id.tv_item_fb_time);
            holder.tv_title = view.findViewById(R.id.tv_title);
            holder.tv_content = view.findViewById(R.id.tv_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        FeedbackResultBean.DataBean model = data.get(i);
        if (model != null) {
            holder.tv_title.setText(model.getType());
            holder.tv_content.setText(model.getContent());
            Date date = new Date();
            date.setTime(model.getCreate_time() * 1000);
            String creatTime = DcDateUtils.toString(date, DcDateUtils.FORMAT_YMD_1);
            holder.tv_item_fb_time.setText("反馈时间 " + creatTime);
        }

//        holder.llyt_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, NoticeDetailActivity.class));
//            }
//        });
        return view;
    }

    public void setData(List<FeedbackResultBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    class ViewHolder {
        private TextView tv_title;
        private TextView tv_item_fb_time;
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
