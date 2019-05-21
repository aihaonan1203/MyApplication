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

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.bean.ReboBean;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.AutoLinefeedLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BqsxSearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoBean> data;
    private Map<Integer,String> label;
    public BqsxSearchAdapter(Context mContext, List<VideoBean> data) {
        this.mContext = mContext;
        label= (Map<Integer, String>) DcAndroidContext.getInstance().getParam("label");
        setData(data);
    }

    public void setData(List<VideoBean> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_film_detail, null);
            holder.ll_parent = view.findViewById(R.id.ll_parent);
            holder.iv_item_icon = view.findViewById(R.id.iv_item_icon);
            holder.tv_item_score = view.findViewById(R.id.tv_item_score);
            holder.tv_item_filmName = view.findViewById(R.id.tv_item_filmName);
            holder.tv_item_playNum = view.findViewById(R.id.tv_item_playNum);
            holder.llyt_item_film = view.findViewById(R.id.llyt_item_film);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final VideoBean model = data.get(i);
        if (model != null) {
            if (model.getLabel() != null) {
                String[] labels = model.getLabel().split(",");
                for (int j = 0; j < labels.length; j++) {
                    final String label = labels[j];
                    TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.childview_film_label, null);
                    textView.setText(this.label.get(Integer.parseInt(label)));
//                    textView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ToastUtil.toastShort(mContext, label);
//                        }
//                    });
                    holder.ll_parent.addView(textView);
                }
            }

            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_icon, LoadImageUtil.getImageOptions(R.drawable.white), R.drawable.white);
            }

            holder.tv_item_score.setText(model.getScore()+"");
            holder.tv_item_filmName.setText(model.getName());
            holder.tv_item_playNum.setText(Utils.getStrPlayNum(model.getViews()));
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
        private AutoLinefeedLayout ll_parent;
        private ImageView iv_item_icon;
        private TextView tv_item_score;
        private TextView tv_item_filmName;
        private TextView tv_item_playNum;
        private LinearLayout llyt_item_film;
    }

}
