package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.dbmodel.IdeasBean;

import java.util.List;

public class MGridViewAdapter extends BaseAdapter {

    List<IdeasBean> mData;
    Context mContext;
    private int checkId;

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public MGridViewAdapter(Context mContext, List<IdeasBean> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public IdeasBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_feedback_bq, null);
            holder.gv_item_title = convertView.findViewById(R.id.gv_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final IdeasBean bean = mData.get(position);
        if (bean != null) {
            holder.gv_item_title.setText(bean.getMsg());

            if (checkId == bean.getBqid()) {
                holder.gv_item_title.setChecked(true);
            } else {
                holder.gv_item_title.setChecked(false);
            }

            holder.gv_item_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onclickSelectId != null) {
                        onclickSelectId.onClickSelectId(bean);
                    }
                }
            });
        }
        return convertView;
    }


    private onclickSelectId onclickSelectId;

    public void setOnclickSelectId(MGridViewAdapter.onclickSelectId onclickSelectId) {
        this.onclickSelectId = onclickSelectId;
    }

    public interface onclickSelectId {
        void onClickSelectId(IdeasBean bean);
    }

    class ViewHolder {
        private CheckBox gv_item_title;
    }
}
