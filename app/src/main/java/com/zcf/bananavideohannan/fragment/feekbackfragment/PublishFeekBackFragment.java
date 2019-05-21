package com.zcf.bananavideohannan.fragment.feekbackfragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.nanchen.compresshelper.CompressHelper;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.MGridViewAdapter;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.dbmodel.IdeasBean;
import com.zcf.bananavideohannan.domain.JsonResultObject;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.util.ToastUtils;

import org.xutils.view.annotation.ContentView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zcf.bananavideohannan.base.Consts.ACTION_URL_PUBLISH_FEEDBACK;
import static com.zcf.bananavideohannan.base.Consts.RESULT_CODE_SUCCESS;

@ContentView(R.layout.fragment_feedback_yijian)
public class PublishFeekBackFragment extends MyBaseFragment implements View.OnClickListener {

    private static String[] item = new String[]{"无法播放", "播放卡顿", "标签错误", "分类错误", "搜索不准", "推荐不准", "无法下载", "其他"};
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridView mGridview;
    private List<IdeasBean> mData;
    private ImageView iv_pic;
    private final int REQUEST_UPLOAD_MANYFILE = 10003;// 上传多图
    private Button mYijianSubmit;
    MGridViewAdapter adapter;
    private String currentType;
    private EditText edt_input;
    private DcAndroidContext app;
    private String uid;
    private String picPath;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        app = (DcAndroidContext) getActivity().getApplicationContext();
        if (app != null && app.getParam(Consts.USER_UID) != null) {
            uid = app.getParam(Consts.USER_UID).toString();
        }

        init();
    }

    private void initView(View view) {
        mGridview = view.findViewById(R.id.yijian_gridview);
        iv_pic = view.findViewById(R.id.iv_pic);
        mYijianSubmit = view.findViewById(R.id.yijian_submit);
        edt_input = view.findViewById(R.id.edt_input);

        iv_pic.setOnClickListener(this);
        mYijianSubmit.setOnClickListener(this);
    }

    private void init() {
        mData = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            mData.add(new IdeasBean(i, item[i]));
        }
        adapter = new MGridViewAdapter(getActivity(), mData);
        adapter.setCheckId(-1);
        mGridview.setAdapter(adapter);
        adapter.setOnclickSelectId(new MGridViewAdapter.onclickSelectId() {
            @Override
            public void onClickSelectId(IdeasBean bean) {
                if (adapter.getCheckId() == bean.getBqid()) {
                    currentType = "";
                    adapter.setCheckId(-1);
                    adapter.notifyDataSetChanged();
                } else {
                    currentType = bean.getMsg();
                    adapter.setCheckId(bean.getBqid());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pic://点击选择图片
                PictureSelector.create(PublishFeekBackFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .selectionMedia(selectList)
                        .forResult(REQUEST_UPLOAD_MANYFILE);
                break;
            case R.id.yijian_submit://提交
                if (TextUtils.isEmpty(currentType)) {
                    ToastUtil.toastShort(getActivity(), "请选择反馈问题类型");
                    return;
                }

                String content = edt_input.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.toastShort(getActivity(), "请填写反馈问题描述");
                    return;
                }

                publishFeedback(currentType, content);
                break;
        }
    }

    private void publishFeedback(String title, String content) {

        HttpParams params = new HttpParams();
        params.put("type", title);
        params.put("content", content);

        File newFile = null;
        if (!TextUtils.isEmpty(picPath)) {
            File file = new File(picPath);
            if (file.exists()) {
                newFile = CompressHelper.getDefault(getActivity()).compressToFile(file);
            }
            params.put("file", newFile);
        } else {
//            params.put("file", "");
        }
        ToastUtils.show(getActivity(), "发布中...");
        OkGo.<String>post(ACTION_URL_PUBLISH_FEEDBACK).tag(this)
                .params(params)
                .headers("token",app.getParam(ContextProperties.REM_TOKEN).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            JsonResultObject jsonobj = GsonUtil.parseJsonWithGson(response.body(), JsonResultObject.class);
                            if (jsonobj != null && RESULT_CODE_SUCCESS == jsonobj.getCode()) {
                                ToastUtil.toastShort(getActivity(), "发布成功");
                            } else {
                                ToastUtil.toastShort(getActivity(), "发布失败");
                            }
                        }
                        ToastUtils.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.dismiss();
                        ToastUtil.toastShort(getActivity(), "发布失败");
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_UPLOAD_MANYFILE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        try {
                            picPath = selectList.get(0).getPath();
                            getBitmapForImgResourse(selectList.get(0).getPath(), iv_pic);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }


    private static Bitmap btp;

    public static void getBitmapForImgResourse(String imgPath, ImageView mImageView) throws IOException {
        InputStream is = new FileInputStream(imgPath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = 10;
        btp = BitmapFactory.decodeStream(is, null, options);
        mImageView.setImageBitmap(btp);
        is.close();
    }
}
