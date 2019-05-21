package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.LabelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelBqGridAdapter1 extends BaseAdapter {
    private Context mContext;
    private List<LabelBean> data;

    private int selected_id;

    private HashMap<Integer, String> mp_parent;

    public void setMp_parent(HashMap<Integer, String> mp_parent) {
        this.mp_parent = mp_parent;
    }

    public ChannelBqGridAdapter1(Context mContext, List<LabelBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_bq_grid1, null);
            holder.tv_item_type = view.findViewById(R.id.tv_item_type);
            holder.iv_bq_icon = view.findViewById(R.id.iv_bq_icon);
            holder.rlyt_item_bq = view.findViewById(R.id.rlyt_item_bq);
            holder.iv_item_selected = view.findViewById(R.id.iv_item_selected);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final LabelBean bean = data.get(i);
        if (bean != null) {

            if (selected_id == bean.getId()) {
                holder.rlyt_item_bq.setBackgroundResource(R.drawable.bg_convers_white_channel_bq_border_ty);
            } else {
                holder.rlyt_item_bq.setBackgroundResource(R.drawable.bg_convers_white_channel_bq_border_ty2);
            }

            if (!TextUtils.isEmpty(bean.getName())) {
                holder.tv_item_type.setText(bean.getName());
            }

            if ("重置".equals(bean.getName())) {
                holder.iv_bq_icon.setVisibility(View.VISIBLE);
            } else {
                holder.iv_bq_icon.setVisibility(View.GONE);
            }

            holder.rlyt_item_bq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemSelectClick != null) {
                        itemSelectClick.onItemSelectedClick(bean);
                    }
                }
            });

            if (mp_parent != null) {
                if (mp_parent.containsKey(bean.getId())) {
                    holder.iv_item_selected.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_item_selected.setVisibility(View.GONE);
                }
            }

        }
        return view;
    }

    public void setItemSelectClick(itemClickSelcted itemSelectClick) {
        this.itemSelectClick = itemSelectClick;
    }

    private itemClickSelcted itemSelectClick;

    public interface itemClickSelcted {
        void onItemSelectedClick(LabelBean bean);
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
        TextView tv_item_type;
        ImageView iv_bq_icon;
        RelativeLayout rlyt_item_bq;
        ImageView iv_item_selected;
    }

    public void setSelected_id(int selected_id) {
        this.selected_id = selected_id;
    }

    public void setData(List<LabelBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }
}
