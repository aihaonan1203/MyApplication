package com.zcf.bananavideohannan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.squareup.picasso.Picasso;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.PlayVideoDetailActivity;
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.bll.DownBll;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.MyUserActionStandardTwo;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.jcvideoplayer_two.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.jcvideoplayer_two.JCVideoPlayerStandard;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FindlistviewAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoBean> data;
    private BvAndroidContent app;
    private String uid;
    private boolean vip;

    public FindlistviewAdapter(Context context, List<VideoBean> data,boolean vip) {
        this.mContext = context;
        setData(data);
        app = (BvAndroidContent) mContext.getApplicationContext();
        if (app != null && app.getParam(Consts.USER_UID) != null) {
            uid = app.getParam(Consts.USER_UID).toString();
        }
        this.vip=vip;
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
    public Object getItem(int position) {
        return data.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_find, null);
            holder.jc_item_player = convertView.findViewById(R.id.jc_item_player);
            holder.iv_item_play = convertView.findViewById(R.id.iv_item_play);
            holder.tv_item_title = convertView.findViewById(R.id.tv_item_title);
            holder.tv_playcount = convertView.findViewById(R.id.tv_item_playcount);
            holder.iv_item_collect = convertView.findViewById(R.id.iv_item_collect);
            holder.iv_item_down = convertView.findViewById(R.id.iv_item_down);
            holder.iv_item_share = convertView.findViewById(R.id.iv_item_share);
            holder.llyt_item_detail = convertView.findViewById(R.id.llyt_item_detail);
            holder.iv_item_imgurl = convertView.findViewById(R.id.iv_item_imgurl);
            holder.llyt_image = convertView.findViewById(R.id.llyt_image);
            holder.llyt_video = convertView.findViewById(R.id.llyt_video);
            holder.tv_detail = convertView.findViewById(R.id.tv_detail);
            holder.iv_item_copy = convertView.findViewById(R.id.iv_item_copy);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VideoBean model = data.get(position);

        if (model != null) {

//            if (!TextUtils.isEmpty(model.getType())) {
//                holder.llyt_image.setVisibility(View.VISIBLE);
//                holder.llyt_video.setVisibility(View.GONE);
//                holder.iv_item_imgurl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        holder.iv_item_imgurl.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                AndroidUtil.openBrowser(mContext, model.getVideo());
//                            }
//                        });
//                    }
//                });
//
//                holder.tv_detail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AndroidUtil.openBrowser(mContext, model.getVideo());
//                    }
//                });
//
//                holder.iv_item_copy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
//                        //创建ClipData对象
//                        ClipData clipData = ClipData.newPlainText("tahome text copy", model.getName());
//                        //添加ClipData对象到剪切板中
//                        clipboardManager.setPrimaryClip(clipData);
//                        ToastUtil.toastShort(mContext, "已复制");
//                    }
//                });
//
//            } else {
                holder.llyt_image.setVisibility(View.GONE);
                holder.llyt_video.setVisibility(View.VISIBLE);

                holder.tv_item_title.setText(model.getName());
                holder.tv_playcount.setText(Utils.getStrPlayNum(model.getViews()));

                if (0 == model.getLikestatus()) {
                    holder.iv_item_collect.setImageResource(R.drawable.icon_xin_1);
                } else {
                    holder.iv_item_collect.setImageResource(R.drawable.icon_xin);
                }

                holder.jc_item_player.setUp(model.getVideofile(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, model.getName());
                Picasso.with(mContext).load(model.getImage()).into(holder.jc_item_player.thumbImageView);
                if (!vip){
                    holder.jc_item_player.startButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.toastLong(mContext,"您不是VIP，不能观看视频呦");
                        }
                    });
                }
                JCVideoPlayer.setJcUserAction(new MyUserActionStandardTwo());

                holder.iv_item_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (1 == model.getLikestatus()) {
                            ToastUtil.toastShort(mContext, "您已收藏过了.");
                            return;
                        }

                        ProgressDialogUtil.showDialog(mContext, "稍等..", true);
                        Disposable d=Network.getVideoApi().setLike(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(),String.valueOf(model.getId()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<ResponseBody>() {
                                    @Override
                                    public void accept(ResponseBody responseBody) throws Exception {
                                        ProgressDialogUtil.dismiss();
                                        JSONObject jsonObject=new JSONObject(responseBody.string());
                                        if (jsonObject.getInt("code")==200) {
                                            ToastUtil.toastShort(mContext, "已添加到我的喜欢!");
                                            holder.iv_item_collect.setImageResource(R.drawable.icon_xin);
                                            model.setLikestatus(1);
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        ProgressDialogUtil.dismiss();
                                        ToastUtil.toastShort(mContext, "添加到我的喜欢失败!");
                                    }
                                });
                    }
                });

                holder.iv_item_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DownBll downBll = new DownBll(mContext);
                        VideoDownDBModel dalmodel = downBll.getByVideoId(String.valueOf(model.getId()));

                        if (dalmodel != null && app.getMpDownData().containsKey(String.valueOf(model.getId()))) {
                            ToastUtil.toastShort(mContext, "当前任务已存在，请耐心等待");
                            mContext.startActivity(new Intent(mContext, DownListActivity.class));
                            return;
                        }
                        if (dalmodel != null
                                && !VideoDownDBModel.DOWN_STATUS_SUCCESS.equals(dalmodel.getStatus())
                                && !app.getMpDownData().containsKey(String.valueOf(dalmodel.getVideo_id()))) {
                            downBll.downLoadFile(dalmodel);
                        } else {
                            if (dalmodel == null) {
                                ToastUtil.toastShort(mContext, "开始下载");
                                VideoDownDBModel newmodel = new VideoDownDBModel();
                                newmodel.setVideo_id(model.getId());
                                newmodel.setFilePath(Consts.VIDEO_CACHE_DIR + Utils.getFileName(model.getVideofile()));
                                newmodel.setImage(model.getImage());
                                newmodel.setName(model.getName());
                                newmodel.setDownurl(model.getId()+"");
                                newmodel.setStatus(VideoDownDBModel.DOWN_STATUS_DOWNING);
                                newmodel.setCreatetime(DcDateUtils.now());
                                downBll.save(newmodel);

                                VideoDownDBModel byVideoId = downBll.getByVideoId(String.valueOf(newmodel.getVideo_id()));

//                        if (!downBll.isLocalVideoExists(String.valueOf(model.getVideo_id()))) {
                                downBll.downLoadFile(byVideoId);
//                        }
                                mContext.startActivity(new Intent(mContext, DownListActivity.class));
                            }
                        }
                    }
                });

                holder.iv_item_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareMsg("香蕉视频", "分享", "大家一起来看香蕉视频吧", null);
                    }
                });


                holder.llyt_item_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(PlayVideoDetailActivity.EXTRA_VIDEROURL, model.getVideofile());
                        bundle.putString(PlayVideoDetailActivity.EXTRA_ICONURL, model.getImage());
                        bundle.putString(PlayVideoDetailActivity.EXTRA_TITLE, model.getName());
                        bundle.putInt(PlayVideoDetailActivity.EXTRA_VIDEO_ID, model.getId());
                        Intent intent = new Intent(mContext, PlayVideoDetailActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });

            }


//        }
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_item_imgurl;
        JCVideoPlayerStandard jc_item_player;
        ImageView iv_item_play;
        TextView tv_item_title;
        TextView tv_playcount;
        ImageView iv_item_collect;
        ImageView iv_item_down;
        ImageView iv_item_share;
        LinearLayout llyt_item_detail;
        LinearLayout llyt_image;
        LinearLayout llyt_video;
        TextView tv_detail;
        ImageView iv_item_copy;
    }

    /**
     * 分享功能
     *
     * @param strTitle   分享弹出框标题
     * @param strSubject 消息标题
     * @param strMsgText 消息内容
     * @param strImgPath 图片路径，不分享图片则传null
     */
    public void shareMsg(String strTitle, String strSubject, String strMsgText, String strImgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (strImgPath == null || strImgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(strImgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");//图片形式
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);//未用到
        intent.putExtra(Intent.EXTRA_TEXT, strMsgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, strTitle));
    }

}
