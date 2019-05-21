package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.AllFilmActivity;
import com.zcf.bananavideohannan.bean.DefTypeBean;

import java.util.ArrayList;
import java.util.List;

public class VideoTypeAdaptger extends BaseAdapter {
    private List<DefTypeBean.DataBean> data;
    private Context mContext;

    public VideoTypeAdaptger(Context context, List<DefTypeBean.DataBean> data) {
        this.mContext = context;
        setData(data);
    }

    public void setData(List<DefTypeBean.DataBean> data) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main_video_type, null);
            holder.tv_item_typeName = view.findViewById(R.id.tv_item_typeName);
            holder.iv_item_img = view.findViewById(R.id.iv_item_img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final DefTypeBean.DataBean model = data.get(i);

        if (model != null) {
            holder.tv_item_typeName.setText(model.getName());
//                LoadImageUtil.loadImg(model.getImgUrl(), holder.iv_item_img, LoadImageUtil.getImageOptions(0), 0);
            switch (model.getId()) {
                case 0:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_8);
                    break;
                case 1:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_3);
                    break;
                case 2:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_4);
                    break;
                case 3:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_7);
                    break;
                case 4:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_4);
                    break;
                case 5:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_3);
                    break;
                case 6:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_7);
                    break;
                case 7:
                    holder.iv_item_img.setImageResource(R.drawable.icon_sizi_1);
                    break;
            }
        }

        holder.iv_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(AllFilmActivity.EXTRA_TYPE_ID, model.getId());
                bundle.putString(AllFilmActivity.EXTRA_TYPE_SORT, model.getName());
                Intent intent = new Intent(mContext, AllFilmActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        private TextView tv_item_typeName;
        private ImageView iv_item_img;
    }
}
