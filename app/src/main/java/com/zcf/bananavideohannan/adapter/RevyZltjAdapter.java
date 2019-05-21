package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.SpecialActivity;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.evenMsg.EventMessage;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.RoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RevyZltjAdapter extends RecyclerView.Adapter<RevyZltjAdapter.ViewHolder> {

    private Context mContext;
    private List<ZltjTjResultBean.DataBean> data;

    public RevyZltjAdapter(Context mContext, List<ZltjTjResultBean.DataBean> data) {
        this.mContext = mContext;
        setData(data);
    }

    public void setData(List<ZltjTjResultBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    @Override
    public RevyZltjAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_zltj_hlv, parent, false);
        RevyZltjAdapter.ViewHolder hol = new RevyZltjAdapter.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ZltjTjResultBean.DataBean model = data.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.img, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            }
            holder.title.setText(model.getName());
            holder.time.setText(Utils.getStrDate(model.getCreatetime()+""));
            holder.msg.setText(model.getDescription());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SpecialActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(101,model));
                    mContext.startActivity(intent);
                }
            });
            holder.rllt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SpecialActivity.class);
                    EventBus.getDefault().postSticky(new EventMessage<>(101,model));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            rllt = itemView.findViewById(R.id.zltj_hlv_rllt);
            title = itemView.findViewById(R.id.zltj_hlv_title);
            time = itemView.findViewById(R.id.zltj_hlv_time);
            msg = itemView.findViewById(R.id.zltj_hlv_msg);
            img = itemView.findViewById(R.id.zltj_hlv_img);
            btn = itemView.findViewById(R.id.zltj_hlv_btn);
        }

        private RelativeLayout rllt;
        private TextView title;
        private TextView time;
        private TextView msg;
        private RoundImageView img;
        private Button btn;
    }
}
