package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.LabelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelBqGridAdapter2 extends BaseAdapter {
    private Context mContext;
    private List<LabelBean.ChildrenBean> data;

    private HashMap<Integer, Integer> mp_child;

    public void setMp_child(HashMap<Integer, Integer> mp_child) {
        this.mp_child = mp_child;
    }

    public ChannelBqGridAdapter2(Context mContext, List<LabelBean.ChildrenBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    public void setData(List<LabelBean.ChildrenBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_bq_grid2, null);
            holder.tv_item_typeName = view.findViewById(R.id.tv_item_typeName);
            holder.iv_item_selected = view.findViewById(R.id.iv_item_selected);
            holder.llyt_item_click = view.findViewById(R.id.llyt_item_click);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final LabelBean.ChildrenBean bean = data.get(i);
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getName())) {
                holder.tv_item_typeName.setText(bean.getName());
            }

            holder.llyt_item_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (childClickSelcted != null) {
                        childClickSelcted.onChildSelectedClick(bean);
                    }
                }
            });

            if (mp_child != null) {
                if (mp_child.containsKey(bean.getId())) {
                    holder.iv_item_selected.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_item_selected.setVisibility(View.GONE);
                }
            }

        }
        return view;
    }

    public void setChildClickSelcted(ChannelBqGridAdapter2.childClickSelcted childClickSelcted) {
        this.childClickSelcted = childClickSelcted;
    }

    private childClickSelcted childClickSelcted;

    public interface childClickSelcted {
        void onChildSelectedClick(LabelBean.ChildrenBean bean);
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
        TextView tv_item_typeName;
        ImageView iv_item_selected;
        LinearLayout llyt_item_click;
    }

}
