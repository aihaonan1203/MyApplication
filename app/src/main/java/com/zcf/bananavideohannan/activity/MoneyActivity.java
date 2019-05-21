package com.zcf.bananavideohannan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nanchen.compresshelper.CompressHelper;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.WithdrawalActivity;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.ToastUtils;
import com.zcf.bananavideohannan.view.dialog.DialogSelectPic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MoneyActivity extends MyBaseActivity implements View.OnClickListener {

    private static final int REQUEST_UPLOAD_HEADIMG = 110;
    private ImageView ivBack;
    private ImageView iv_image;
    private TextView tvRight;
    private RadioButton rbWx;
    private Button btnSubmit;
    private EditText etMoney;
    private EditText etNumber;
    private EditText et_phone;
    private EditText et_name;
    private LoginBean.DataBean.UserinfoBean userinfo;
    private List<LocalMedia> selectList;
    private File newFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        initView();
    }

    private void initView() {
        etMoney = findViewById(R.id.et_money);
        etNumber = findViewById(R.id.et_number);
        ivBack = findViewById(R.id.iv_back);
        iv_image = findViewById(R.id.iv_image);
        et_phone = findViewById(R.id.et_phone);
        et_name = findViewById(R.id.et_name);
        tvRight = findViewById(R.id.tv_right);
        rbWx = findViewById(R.id.rb_wx);
        btnSubmit = findViewById(R.id.btn_submit);
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        iv_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(MoneyActivity.this, WithdrawalActivity.class));
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.iv_image:
                openPictureSelect();
                break;
        }
    }


    private void openPictureSelect() {
        selectList = new ArrayList<>();
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .previewVideo(false)
                .enableCrop(true)
                .freeStyleCropEnabled(false)
                .circleDimmedLayer(false)
                .isCamera(true)
                .selectionMedia(selectList)
                .forResult(REQUEST_UPLOAD_HEADIMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_UPLOAD_HEADIMG:
                    // 图片、视频、音频选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        for (LocalMedia media : selectList) {
                            String path;
                            if (media.isCut()) {
                                path = media.getCutPath();
                            } else {
                                path = media.getPath();
                            }

                            File file = new File(path);
                            long d = file.length();
                            newFile = CompressHelper.getDefault(mContext).compressToFile(file);
                            Glide.with(mContext).load(newFile).into(iv_image);
                            long length = newFile.length();
                            length++;
                        }
                    }
                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    @SuppressLint("CheckResult")
    private void submit() {
        final String money = etMoney.getText().toString().trim();
        final String number = etNumber.getText().toString().trim();
        final String name = et_name.getText().toString().trim();
        final String phone = et_phone.getText().toString().trim();
        if (newFile==null){
            Toast.makeText(mContext, "请先选择照片！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (money.isEmpty() || number.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(mContext, "提现所有信息不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        String type = "";
        if (rbWx.isChecked()) {
            type = "1";
        } else {
            type = "2";
        }
        final int t=Integer.parseInt(type);
        final String token = DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString();
        ToastUtils.show(MoneyActivity.this,"正在发起体现...");
        OkGo.<String>post(DOMAIN_URL+"/index.php/api/user/payurl")
                .headers("token",token)
                .params("payurl",newFile)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ToastUtils.dismiss();
                        try {
                            final JSONObject jsonObject=new JSONObject(response.body());
                            String data = jsonObject.getString("data");
                            Network.getVideoApi().withdraw(token, t, money, phone, name, data, number,"")
                                    .compose(MyBaseActivity.<ResponseBody>applySchedulers())
                                    .subscribe(new Consumer<ResponseBody>() {
                                        @Override
                                        public void accept(ResponseBody responseBody) throws Exception {
                                            JSONObject jsonObject1=new JSONObject(responseBody.string());
                                            if (jsonObject.getInt("code")==200) {
                                                ToastUtil.toastShort(MoneyActivity.this,"发起提现成功！");
                                                startActivity(new Intent(MoneyActivity.this, WithdrawalActivity.class));

                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

                                        }
                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.dismiss();
                    }
                });

//        Disposable d =
//                .compose(MyBaseActivity.<ResponseBody>applySchedulers())
//                .subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        JSONObject jsonObject = new JSONObject(responseBody.string());
//                        if (jsonObject.getInt("code") == 200) {
//                            Toast.makeText(mContext, "申请成功!", Toast.LENGTH_SHORT).show();
//                            float i = Float.parseFloat(userinfo.getMoney());
//                            float i1 = Float.parseFloat(money);
//                            userinfo.setMoney(String.valueOf(i - i1));
//                            EventBus.getDefault().post(userinfo);
//                            finish();
//                        } else {
//                            Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(mContext, "申请失败,请检查网络连接!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}

