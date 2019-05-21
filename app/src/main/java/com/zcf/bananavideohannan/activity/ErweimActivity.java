package com.zcf.bananavideohannan.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.domain.JsonResultObject;
import com.zcf.bananavideohannan.util.EncodingUtils;
import com.zcf.bananavideohannan.util.GsonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;

@ContentView(R.layout.activity_erweim)
public class ErweimActivity extends MyBaseActivity {
    @ViewInject(R.id.iv_qr)
    private ImageView iv_qr;

    @ViewInject(R.id.rlyt_save)
    private RelativeLayout rlyt_save;

    @ViewInject(R.id.rlyt_save_success)
    private RelativeLayout rlyt_save_success;

    @ViewInject(R.id.rlyt_copy)
    private RelativeLayout rlyt_copy;

    @ViewInject(R.id.rlyt_copy_success)
    private RelativeLayout rlyt_copy_success;

    @ViewInject(R.id.tv_code)
    private TextView tv_code;

    private String url = "";

    private Bitmap bitmap;
    private String uid;

    @ViewInject(R.id.guanwnagurl)
    private TextView guanwnagurl;

    private Context context;

    private LoginBean.DataBean.UserinfoBean userinfo;
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        url=userinfo.getUrl();
        guanwnagurl.setText("官网：" + userinfo.getUrl());
        tv_code.setText(userinfo.getInvite());
        uid=String.valueOf(userinfo.getUser_id());
        newQr();
    }

    private void newQr() {
        if (TextUtils.isEmpty(url)) {
            url = userinfo.getUrl();
        }
        bitmap = EncodingUtils.createQRCode(url, 500, 500, BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        iv_qr.setImageBitmap(bitmap);
    }

    @Event(R.id.rlyt_save)
    private void saveImg(View view) {
        saveaa();
    }

    @Event(R.id.rlyt_copy)
    private void copyUrl(View view) {
        Copy(url);
    }

    public void Copy(String url) {
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("tahome text copy", url.toString());
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
        ToastUtil.toastShort(mContext, "已复制");
        rlyt_copy_success.setVisibility(View.VISIBLE);
        rlyt_copy.setVisibility(View.GONE);
    }

    private void saveBitmap(Context context, String imagePath, String imagename) throws IOException {
        try {
            ContentResolver cr = context.getContentResolver();
            String url = MediaStore.Images.Media.insertImage(cr, imagePath, imagename, "");
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(url));
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveaa() {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
        ToastUtil.toastShort(mContext, "已保存到相册");
        rlyt_save_success.setVisibility(View.VISIBLE);
        rlyt_save.setVisibility(View.GONE);
//        addLookNumber(uid);

    }

    private void addLookNumber(String uid) {
        OkGo.<String>post(Consts.SAVE_ERWEICODE_Number).tag(this)
                .params("uid", uid).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (200 == response.code()) {
                    LOG.d("WWWWWWWWWWWWWWWWWW", response.body());
                    JsonResultObject jsonResultObject = GsonUtil.parseJsonWithGson(response.body(), JsonResultObject.class);
                    Toast.makeText(context, jsonResultObject.getData(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }
}
