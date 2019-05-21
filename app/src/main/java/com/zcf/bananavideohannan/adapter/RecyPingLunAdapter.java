package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.CommentlistBean;
import com.zcf.bananavideohannan.dbmodel.FilmPinglunDBModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyPingLunAdapter extends RecyclerView.Adapter<RecyPingLunAdapter.ViewHolder> {
    private Context mContext;
    private List<CommentlistBean> dbModels;

    public RecyPingLunAdapter(Context mContext, List<CommentlistBean> dbModels) {
        this.mContext = mContext;
        setDbModels(dbModels);
    }

    public void setDbModels(List<CommentlistBean> dbModels) {
        if(dbModels==null){
            dbModels = new ArrayList<>();
        }
        this.dbModels = dbModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_film_content, parent, false);
        ViewHolder hol = new ViewHolder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentlistBean model = dbModels.get(position);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getAvatar())) {
                LoadImageUtil.loadImg(model.getAvatar(), holder.iv_item_headImg, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            }
            holder.tv_item_name.setText(model.getNickname());
            Date pDate = DcDateUtils.toDate(model.getNickname(), DcDateUtils.FORMAT_DATE_TIME_1);
            holder.tv_item_lastDate.setText(DcDateUtils.getShowTime(pDate));
            holder.iv_sex.setImageResource(FilmPinglunDBModel.nan.equals(model.getSex()) ? R.drawable.icon_nan : R.drawable.icon_nv);
            holder.tv_item_content.setText(model.getComment());
//            holder.tv_item_count.setText(String.valueOf(model.get()));
        }
    }

    @Override
    public int getItemCount() {
        return dbModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            iv_item_headImg = itemView.findViewById(R.id.iv_item_headImg);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_lastDate = itemView.findViewById(R.id.tv_item_lastDate);
            iv_sex = itemView.findViewById(R.id.iv_sex);
            tv_item_content = itemView.findViewById(R.id.tv_item_content);
            tv_item_count = itemView.findViewById(R.id.tv_item_count);
        }

        private ImageView iv_item_headImg;// 头像
        private TextView tv_item_name; // 名字
        private TextView tv_item_lastDate;// 发布时间
        private ImageView iv_sex;// 性别
        private TextView tv_item_content;// 评论内容
        private TextView tv_item_count;// 点赞数量
    }

}
