package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoActivity;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.bll.DownBll;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfflineAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoDownDBModel> data;
    private boolean isShowCheck;
    private HashMap<String, String> mpdata_select = new HashMap<>();
    private BvAndroidContent app;


    public OfflineAdapter(Context mContext, List<VideoDownDBModel> data) {
        this.mContext = mContext;
        setData(data);
        app = (BvAndroidContent) mContext.getApplicationContext();
    }

    public void setData(List<VideoDownDBModel> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_offline_down, null);
            holder.iv_item_icon = view.findViewById(R.id.iv_his_item_icon);
            holder.tv_item_filmName = view.findViewById(R.id.tv_item_filmName);
            holder.tv_item_timelong = view.findViewById(R.id.tv_item_timelong);
            holder.cb_selected = view.findViewById(R.id.cb_selected);
            holder.down_progress = view.findViewById(R.id.down_progress);
            holder.llyt_down = view.findViewById(R.id.llyt_down);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final VideoDownDBModel model = data.get(i);

        if (model.getIsDowning()) {
            holder.down_progress.setVisibility(View.VISIBLE);
            holder.down_progress.setProgress(model.getProgress());
        } else {
            holder.down_progress.setVisibility(View.GONE);
        }

        if (isShowCheck) {
            holder.cb_selected.setVisibility(View.VISIBLE);
            if (mpdata_select != null && mpdata_select.containsKey(String.valueOf(model.getVideo_id()))) {
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
                    mpdata_select.put(String.valueOf(model.getVideo_id()), "");
                } else {
                    mpdata_select.remove(String.valueOf(model.getVideo_id()));
                }
            }
        });

        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                LoadImageUtil.loadImg(model.getImage(), holder.iv_item_icon, LoadImageUtil.getImageOptions(R.drawable.white), R.drawable.white);
            }
            holder.tv_item_filmName.setText(model.getName());
            holder.down_progress.setProgress(model.getProgress());
            holder.llyt_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (model.getProgress() != 100
                            && !app.getMpDownData().containsKey(String.valueOf(model.getVideo_id()))
                            && !VideoDownDBModel.DOWN_STATUS_SUCCESS.equals(model.getStatus())) {
                        DownBll downBll = new DownBll(mContext);
                        model.setStatus(VideoDownDBModel.DOWN_STATUS_DOWNING);
                        downBll.downLoadFile(model);
                        ToastUtil.toastShort(mContext, "开始下载");
                    } else {
                        DownBll downBll = new DownBll(mContext);
                        VideoDownDBModel dalmodel = downBll.getByVideoId(String.valueOf(model.getVideo_id()));
                        if (dalmodel != null && app.getMpDownData().containsKey(String.valueOf(model.getVideo_id()))) {
                            ToastUtil.toastShort(mContext, "当前任务已存在，请耐心等待");
                            return;
                        }
                        if (VideoDownDBModel.DOWN_STATUS_SUCCESS.equals(model.getStatus())) {
                            Bundle bundle = new Bundle();
                            bundle.putString(PlayVideoActivity.EXTRA_VIDEO_PATH, model.getFilePath());
                            bundle.putString(PlayVideoActivity.EXTRA_VIDEO_IMAGE, model.getImage());
                            bundle.putString(PlayVideoActivity.EXTRA_VIDEO_NAME, model.getName());
                            Intent intent = new Intent(mContext, PlayVideoActivity.class);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
//            holder.tv_item_timelong.setText(model.get());
        }
        return view;
    }

    class ViewHolder {
        private ImageView iv_item_icon;
        private TextView tv_item_filmName;
        private TextView tv_item_timelong;
        private CheckBox cb_selected;
        private ProgressBar down_progress;
        private LinearLayout llyt_down;
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

    public void setMpdata_select(HashMap<String, String> mpdata_select) {
        this.mpdata_select = mpdata_select;
    }

    public void setShowCheck(boolean showCheck) {
        isShowCheck = showCheck;
    }

    public boolean getIsShowCheck() {
        return isShowCheck;
    }
}
