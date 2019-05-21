package com.zcf.bananavideohannan.bll;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zcf.bananavideohannan.activity.my.DownListActivity;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.dal.DownVideoDal;
import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;
import com.zcf.bananavideohannan.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownBll {
    private Context mContext;
    private BvAndroidContent app;
    private DownVideoDal dal;

    public DownBll(Context mContext) {
        this.mContext = mContext;
        app = (BvAndroidContent) mContext.getApplicationContext();
        dal = new DownVideoDal();
    }

    public List<VideoDownDBModel> getList() {
        List<VideoDownDBModel> models = dal.getList();
        List<VideoDownDBModel> newmodels = new ArrayList<>();

        if (models != null && models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                VideoDownDBModel model = models.get(i);
                if (isLocalVideoExists(String.valueOf(model.getVideo_id()))) {
                    model.setDowning(false);
                } else {
                    model.setDowning(true);
                }
                newmodels.add(model);
            }
        }
        return newmodels;
    }

    public void save(VideoDownDBModel model) {
        dal.save(model);
    }

    /**
     */
    public void downLoadFile(final VideoDownDBModel model) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    OkGo.<File>get(model.getDownurl())
                            .tag(this)
                            .execute(new FileCallback(Consts.VIDEO_CACHE_DIR, Utils.getFileName(model.getDownurl())) {
                                @Override
                                public void onSuccess(Response<File> response) {
                                    model.setProgress(100);
                                    model.setStatus(VideoDownDBModel.DOWN_STATUS_SUCCESS);
                                    saveOrUpdate(model);
                                    app.getMpDownData().remove(String.valueOf(model.getVideo_id()));
                                    ContextProperties.writeRemember(mContext, String.valueOf(model.getVideo_id()), VideoDownDBModel.DOWN_STATUS_SUCCESS);
                                    ToastUtil.toastShort(mContext, "下载完成");
                                    if (DownListActivity.handler != null) {
                                        DownListActivity.handler.sendEmptyMessage(2);
                                    }
                                }

                                @Override
                                public void onFinish() {

                                    super.onFinish();
                                }

                                @Override
                                public void onError(Response<File> response) {
                                    app.getMpDownData().remove(String.valueOf(model.getVideo_id()));
                                    super.onError(response);
                                }

                                @Override
                                public void onStart(Request<File, ? extends Request> request) {
                                    app.getMpDownData().put(String.valueOf(model.getVideo_id()), "");
                                    ContextProperties.writeRemember(mContext, String.valueOf(model.getVideo_id()), VideoDownDBModel.DOWN_STATUS_PAUSEED);
                                    super.onStart(request);
                                }

                                @Override
                                public void downloadProgress(Progress progress) {
                                    super.downloadProgress(progress);
                                    float dLProgress = progress.fraction;
                                    if (DownListActivity.handler != null) {
                                        Message msg = new Message();
                                        msg.what = DownListActivity.HANDLER_NOTICE_DOWN_PROGRSS;

                                        Bundle bundle = new Bundle();
                                        HashMap<String, Integer> idprogress = new HashMap<>();

//                                        bundle.putString(DownListActivity.EXTRA_VIDEO_ID, String.valueOf(idprogress));
                                        bundle.putString(DownListActivity.EXTRA_VIDEO_ID, String.valueOf(model.getVideo_id()));
                                        bundle.putString(DownListActivity.EXTRA_VIDEO_URL, model.getDownurl());
                                        bundle.putInt(DownListActivity.EXTRA_VIDEO_PROGRESS, (int) (dLProgress * 100));
                                        msg.setData(bundle);
                                        DownListActivity.handler.sendMessage(msg);
                                    }

                                    Log.e("下载视频==", "" + dLProgress);
                                }

                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread = null;
    }

    public VideoDownDBModel getByVideoId(String id) {
        return dal.getById(id);
    }

    public boolean isLocalVideoExists(String id) {
        VideoDownDBModel model = getByVideoId(id);
        if (model != null && model.getProgress() == 100) {
            String filePath = model.getFilePath();
            if (!TextUtils.isEmpty(filePath)) {
                File file = new File(filePath);
                if (file.exists() && file.isFile() && (file.getName().endsWith("mp4")
                        || file.getName().endsWith("rmvb")
                        || file.getName().endsWith("amv")
                        || file.getName().endsWith("avi ")
                        || file.getName().endsWith("rm")
                        || file.getName().endsWith("flv"))) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void saveOrUpdate(VideoDownDBModel entity) {
        dal.saveOrUpdate(entity);
    }

    public void deleteById(String video_id) {
        dal.deleteById(video_id);
    }
}
