package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.DefTypeBean;

import java.util.HashMap;
import java.util.List;

public class RevyAllFilmTypeAdapter extends RecyclerView.Adapter<RevyAllFilmTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<DefTypeBean.DataBean> dbModels;
    private HashMap<Integer, String> mp_selected;
    private String sort;

    public RevyAllFilmTypeAdapter(Context mContext, List<DefTypeBean.DataBean> dbModels) {
        this.mContext = mContext;
        this.dbModels = dbModels;
    }

    public void setMp_selected(HashMap<Integer, String> mp_selected) {
        this.mp_selected = mp_selected;
    }

    @Override
    public RevyAllFilmTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_allfilm_type, parent, false);
        RevyAllFilmTypeAdapter.ViewHolder hol = new RevyAllFilmTypeAdapter.ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DefTypeBean.DataBean model = dbModels.get(position);
        if (model != null) {
            int typeid = model.getId();
            for(Integer key:mp_selected.keySet()){
                sort = mp_selected.get(key);
                break;
            }
            if (mp_selected.containsKey(typeid) || model.getName().equals(sort)) {
                holder.tv_item_allfilm_typename.setBackgroundResource(R.drawable.bg_convers_video_biaoqian1);
                holder.tv_item_allfilm_typename.setTextColor(mContext.getResources().getColor(R.color.main_bq_color));
            } else {
                holder.tv_item_allfilm_typename.setBackgroundResource(R.drawable.bg_convers_white_10dp);
                holder.tv_item_allfilm_typename.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            holder.tv_item_allfilm_typename.setText(model.getName());

            holder.tv_item_allfilm_typename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickChooseType != null) {
                        clickChooseType.clickChooseType(model.getId(), model.getName());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dbModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item_allfilm_typename = itemView.findViewById(R.id.tv_item_allfilm_typename);
        }

        private TextView tv_item_allfilm_typename;
    }


    public interface onClickChooseType {
        void clickChooseType(int typeid, String sort);
    }

    private onClickChooseType clickChooseType;

    public void setClickChooseType(onClickChooseType clickChooseType) {
        this.clickChooseType = clickChooseType;
    }
}
