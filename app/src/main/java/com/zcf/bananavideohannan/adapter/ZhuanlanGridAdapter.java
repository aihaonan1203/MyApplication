package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.zcf.bananavideohannan.activity.SpecialActivity;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class ZhuanlanGridAdapter extends BaseAdapter {

    private List<ZltjTjResultBean.DataBean> data;
    private Context mContext;

    public ZhuanlanGridAdapter(Context context, List<ZltjTjResultBean.DataBean> data) {
        setData(data);
        mContext = context;
    }

    public void setData(List<ZltjTjResultBean.DataBean> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_channel_zl_gridview, null);
            holder.tv_item_zl_title = convertView.findViewById(R.id.tv_item_zl_title);
            holder.iv_item_zl_img = convertView.findViewById(R.id.iv_item_zl_img);
            holder.llyt_nvdetail = convertView.findViewById(R.id.llyt_nvdetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ZltjTjResultBean.DataBean model = data.get(position);
        if (model != null) {
            holder.tv_item_zl_title.setText(model.getName());
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_zl_img, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            } else {
                holder.iv_item_zl_img.setImageResource(R.drawable.moren_headimg);
            }

            holder.llyt_nvdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SpecialActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(101,model));
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        private TextView tv_item_zl_title;
        private ImageView iv_item_zl_img;
        private LinearLayout llyt_nvdetail;
    }
}
