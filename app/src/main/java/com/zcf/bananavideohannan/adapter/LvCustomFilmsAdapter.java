package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.SpecialActivity;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.view.MyGridViewNoScroll;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LvCustomFilmsAdapter extends BaseAdapter {
    private Context mContext;
    private List<ZltjTjResultBean.DataBean> data;

    public LvCustomFilmsAdapter(Context mContext, List<ZltjTjResultBean.DataBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_def_custom_films, null);
            holder.rlyt_get_more = view.findViewById(R.id.rlyt_get_more);
            holder.gr_item_films = view.findViewById(R.id.gr_item_films);
            holder.tv_item_custom_title = view.findViewById(R.id.tv_item_custom_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ZltjTjResultBean.DataBean model = data.get(i);
        if (model != null) {
            holder.rlyt_get_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SpecialActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(101,model));
                    mContext.startActivity(intent);
//                    mContext.startActivity(new Intent(mContext, AllFilmActivity.class));
                }
            });

            if (model.getValue() != null) {
                DefCustomTagAdapter adapter = new DefCustomTagAdapter(mContext, model.getValue());
                holder.gr_item_films.setAdapter(adapter);
            }

            holder.tv_item_custom_title.setText(model.getName());
        }
        return view;
    }

    public void setData(List<ZltjTjResultBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    class ViewHolder {
        private RelativeLayout rlyt_get_more;
        private MyGridViewNoScroll gr_item_films;
        private TextView tv_item_custom_title;
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
