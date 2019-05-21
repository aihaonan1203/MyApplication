package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.bean.LikeBean;
import com.zcf.bananavideohannan.bean.ReboBean;
import com.zcf.bananavideohannan.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class PlayHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<LikeBean> data;
    private boolean isShowCheck;
    private HashMap<String, String> mpdata_select = new HashMap<>();
    private BvAndroidContent app;

    public PlayHistoryAdapter(Context mContext, List<LikeBean> data) {
        this.mContext = mContext;
        setData(data);
        app = (BvAndroidContent) mContext.getApplicationContext();
    }

    public void setMpdata_select(HashMap<String, String> mpdata_select) {
        if (mpdata_select == null) {
            mpdata_select = new HashMap<>();
        }
        this.mpdata_select = mpdata_select;
    }

    public void setData(List<LikeBean> data) {
        this.data = data;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_play_history, null);
            holder.iv_item_icon = view.findViewById(R.id.iv_his_item_icon);
            holder.tv_item_filmName = view.findViewById(R.id.tv_item_filmName);
            holder.tv_item_timelong = view.findViewById(R.id.tv_item_timelong);
            holder.cb_selected = view.findViewById(R.id.cb_selected);
            holder.llyt_item_film = view.findViewById(R.id.llyt_item_film);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final LikeBean model = data.get(i);

        if (isShowCheck) {
            holder.cb_selected.setVisibility(View.VISIBLE);
            if (mpdata_select != null && mpdata_select.get(String.valueOf(model.getId())) != null) {
                holder.cb_selected.setChecked(true);
            } else {
                holder.cb_selected.setChecked(false);
            }
        } else {
            holder.cb_selected.setVisibility(View.GONE);
        }


        holder.cb_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mpdata_select.put(String.valueOf(model.getId()), "");
                } else {
                    mpdata_select.remove(String.valueOf(model.getId()));
                }
//                ToastUtil.toastShort(mContext, mpdata_select.toString());
                app.setMp_selected(mpdata_select);
            }
        });

        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_icon, LoadImageUtil.getImageOptions(R.drawable.white), R.drawable.white);
            }
            holder.tv_item_filmName.setText(model.getName());
            holder.tv_item_timelong.setText(Utils.stampToDate(model.getCreate_time()));

            holder.llyt_item_film.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReboBean.DataBean dataBean=new ReboBean.DataBean(model.getId(),model.getName(),model.getImage(),model.getVideofile(),
                            model.getFanhao(),model.getActor(),model.getCategory_id(),model.getKeyword(),model.getSpecial(),model.getLabel(),
                            model.getViews(),model.getScore(),model.getLikes(),model.getContent(),model.getGoodnum(),model.getBadnum(),model.getCreate_time(), model.getUpdate_time());
                    Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                    EventBus.getDefault().postSticky(dataBean);
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

    class ViewHolder {
        private ImageView iv_item_icon;
        private TextView tv_item_filmName;
        private TextView tv_item_timelong;
        private CheckBox cb_selected;
        private LinearLayout llyt_item_film;
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

    public HashMap<String, String> getMpdata_select() {
        return mpdata_select;
    }

    public void setShowCheck(boolean showCheck) {
        isShowCheck = showCheck;
    }

    public boolean getIsShowCheck() {
        return isShowCheck;
    }
}
