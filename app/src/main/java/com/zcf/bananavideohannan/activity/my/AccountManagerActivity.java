package com.zcf.bananavideohannan.activity.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.LoadImageUtil;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
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
import com.zcf.bananavideohannan.activity.MoneyActivity;
import com.zcf.bananavideohannan.activity.login.EditPwdActivity;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.XCRoundImageView;
import com.zcf.bananavideohannan.view.dialog.DialogEditName;
import com.zcf.bananavideohannan.view.dialog.DialogSelectPic;
import com.zcf.bananavideohannan.view.dialog.DialogSelectSex;
import com.zcf.bananavideohannan.view.dialog.Dialoglogout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.List;

@ContentView(R.layout.activity_account_manager)
public class AccountManagerActivity extends MyBaseActivity {

    private List<LocalMedia> selectList;
    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bvideo/image/";
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "image_cache/";
    private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;
    private static final String IMAGE_FILE_NAME = "faceImage.jpeg";
    private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";
    File fileone = null;
    File filetwo = null;

    // 常量定义
    private static final int REQUEST_UPLOAD_HEADIMG = 1;
    public static final int TAKE_A_PICTURE = 2;
    public static final int SET_PICTURE = 3;

    @ViewInject(R.id.iv_headimg)
    private XCRoundImageView iv_headimg;

    @ViewInject(R.id.tv_nickname)
    private TextView tv_nickname;

    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    @ViewInject(R.id.rlyt_nickname_point)
    private RelativeLayout rlyt_nickname_point;

    @ViewInject(R.id.rlyt_sex_point)
    private RelativeLayout rlyt_sex_point;

    private String uid;
    private LoginBean.DataBean.UserinfoBean userinfo;
//    private RelativeLayout rlytMoney;
//    private TextView tvMoney;
//    private RelativeLayout rlytJingbi;
//    private TextView tvJingbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initView();
        initFile();
        initPersonData();

        if (app != null && app.getParam(Consts.USER_UID) != null) {
            uid = app.getParam(Consts.USER_UID).toString();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
//        if (userinfo!=null&&tvMoney!=null){
//            tvMoney.setText("¥"+userinfo.getMoney());
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_A_PICTURE:
                    if (resultCode == RESULT_OK) {
                        cameraCropImageUri(Uri.fromFile(new File(IMGPATH,
                                IMAGE_FILE_NAME)));
                    }
                    break;
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
                            File newFile = CompressHelper.getDefault(mContext).compressToFile(file);
                            updatePersonInfo(newFile);
                        }
                    }
                    break;
                case SET_PICTURE:
//                    if (resultCode == RESULT_OK && null != data) {
//                        File file = new File(IMGPATH, IMAGE_FILE_NAME);
//                        File newFile = CompressHelper.getDefault(mContext).compressToFile(file);
//                        uploadAvatar(newFile);
//                    }
                    break;
            }
        }
    }

    @Event(R.id.rlyt_money)
    private void openMoney(View v) {
        startActivity(new Intent(AccountManagerActivity.this,MoneyActivity.class));
    }

    @Event(R.id.rlyt_jingbi)
    private void openJingBi(View v) {
        startActivity(new Intent(AccountManagerActivity.this,MyMoneyActivity.class));
    }

    @Event(R.id.rlyt_accountManager)
    private void openPicDialog(View v) {
        showPicDialog();
    }

    @Event(R.id.rlyt_edit_sex)
    private void openSexDialog(View v) {
        showSexDialog();
    }

    @Event(R.id.rlyt_edit_nickname)
    private void openEditDialog(View v) {
        showEditNameDialog();
    }

    @Event(R.id.btn_login_out)
    private void loginout(View v) {
        showLoginoutDialog();
    }

    private DialogSelectPic dialogSelectPic;
    private DialogSelectSex dialogSelectSex;
    private DialogEditName dialogEditName;
    private Dialoglogout dialoglogout;

    /**
     * 弹出照片选择dialog
     */
    private void showPicDialog() {
        dialogSelectPic = new DialogSelectPic(this);
        dialogSelectPic.setCanceledOnTouchOutside(true);
        dialogSelectPic.setView(new EditText(mContext));
        dialogSelectPic.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogSelectPic.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialogSelectPic.getWindow().setAttributes(lp);

        dialogSelectPic.setClickOpenPicSelect(new DialogSelectPic.onClickOpenPicSelect() {
            @Override
            public void openPicSelect() {
                openPictureSelect();
            }

            @Override
            public void openCamera() {
                openPCamera();
            }
        });
    }

    /**
     * 弹出昵称输入框
     */
    private void showEditNameDialog() {
        dialogEditName = new DialogEditName(this);
        dialogEditName.setCanceledOnTouchOutside(true);
        dialogEditName.setView(new EditText(mContext));
        dialogEditName.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogEditName.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialogEditName.getWindow().setAttributes(lp);

        dialogEditName.setOnEditName(new DialogEditName.onEditNickName() {
            @Override
            public void onEditName(String name) {
                if (name.length() > 7) {
                    ToastUtil.toastShort(mContext, "您的名字太长了，请限制在7个字符");
                    return;
                }
                String appname = "";
                if (app != null && app.getParam(Consts.USER_NICK_NAME) != null) {
                    appname = app.getParam(Consts.USER_NICK_NAME).toString();
                }
                if (!name.equals(appname)) {
                    updatePersonInfo("nickname", name);
                }
                dialogEditName.dismiss();
            }
        });
    }

    /**
     * 弹出性别选择dialog
     */
    private void showSexDialog() {
        dialogSelectSex = new DialogSelectSex(this);
        dialogSelectSex.setCanceledOnTouchOutside(true);
        dialogSelectSex.setView(new EditText(mContext));
        dialogSelectSex.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogSelectSex.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialogSelectSex.getWindow().setAttributes(lp);

        dialogSelectSex.setClickOpenPicSelect(new DialogSelectSex.onClickOpenPicSelect() {
            @Override
            public void onSelectSex(String Sex) {
                updatePersonInfo("sex", Sex);
                dialogSelectSex.dismiss();
            }
        });
    }

    /**
     * 弹出退出登录
     */
    private void showLoginoutDialog() {
        dialoglogout = new Dialoglogout(this);
        dialoglogout.setCanceledOnTouchOutside(true);
        dialoglogout.setView(new EditText(mContext));
        dialoglogout.show();

        // 去除左右边距
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialoglogout.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialoglogout.getWindow().setAttributes(lp);
    }

    private void openPictureSelect() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageSpanCount(3)
                .maxSelectNum(1)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .previewVideo(false)
                .enableCrop(true)
                .freeStyleCropEnabled(false)
                .circleDimmedLayer(false)
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .isCamera(true)
                .isZoomAnim(true)
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)
                .cropWH(600, 600)
                .withAspectRatio(1, 1)
                .selectionMedia(selectList)
                .minimumCompressSize(100)
                .isDragFrame(true)
                .forResult(REQUEST_UPLOAD_HEADIMG);
    }

    private void openPCamera() {
        // 从相机
        boolean sdCardReadFlag = true;
        try {
            if (!fileone.exists() || !filetwo.exists()) {
                sdCardReadFlag = false;
            }
        } catch (Exception e) {
            sdCardReadFlag = false;
        }

        if (sdCardReadFlag) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
            startActivityForResult(intent, TAKE_A_PICTURE);
        } else {
            Toast.makeText(mContext, "友情提示:无SD卡,请插入SD卡后重试!", Toast.LENGTH_LONG).show();
        }
    }

//    private void uploadAvatar(File file) {
//        ProgressDialogUtil.showDialog(mContext, "修改中...", true);
//        OkGo.<String>post(Consts.ACTION_URL_UPLOAD_HEADIMG).tag(this).isMultipart(true)
//                .params("avatar", file)
//                .params("uid", uid)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            HeadImgBean bean = GsonUtil.parseJsonWithGson(response.body(), HeadImgBean.class);
//                            if (bean != null) {
//                                if (!TextUtils.isEmpty(bean.getImage())) {
//                                    ToastUtil.toastShort(mContext, "上传成功");
//                                    LoadImageUtil.loadImg(bean.getImage(), iv_headimg, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
//                                    ContextProperties.writeRemember(mContext, ContextProperties.REM_HEAD_IMG, bean.getImage());
//                                    app.setParam(Consts.USER_ISREFRESH_PERSONINFO, Consts.USER_FRESH_YES);
//                                }
//                            }
//                        }
//                        dialogSelectPic.dismiss();
//                        ProgressDialogUtil.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        ToastUtil.toastShort(mContext, "上传失败");
//                        dialogSelectPic.dismiss();
//                        ProgressDialogUtil.dismiss();
//                        super.onError(response);
//                    }
//
//                });
//    }

    private void updatePersonInfo(File avatar) {
        ProgressDialogUtil.showDialog(mContext, "修改中...", true);
        OkGo.<String>post(Consts.ACTION_URL_UPDATE_USERINFO).tag(this).isMultipart(true)
                .headers("token", userinfo.getToken())
                .params("avatar", avatar)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        httpLogin(userinfo.getMobile(), DcAndroidContext.getInstance().getParam(ContextProperties.REM_PASSWORD).toString());
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressDialogUtil.dismiss();
                        super.onError(response);
                    }
                });
    }

    private void updatePersonInfo(String type, String data) {
        ProgressDialogUtil.showDialog(mContext, "修改中...", true);
        OkGo.<String>post(Consts.ACTION_URL_UPDATE_USERINFO).tag(this).isMultipart(true)
                .headers("token", userinfo.getToken())
                .params(type, data)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        httpLogin(userinfo.getMobile(), DcAndroidContext.getInstance().getParam(ContextProperties.REM_PASSWORD).toString());
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressDialogUtil.dismiss();
                        super.onError(response);
                    }
                });
    }

    private void httpLogin(final String account, final String pwd) {
        OkGo.<String>post(Consts.ACTION_URL_LOGIN).tag(this).isMultipart(true)
                .params("account", account)
                .params("password", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (200 == response.code()) {
                                LoginBean bean = GsonUtil.parseJsonWithGson(response.body(), LoginBean.class);
                                if (bean != null) {
                                    if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                        // 登录成功
                                        ContextProperties.remeberCache(mContext, account, pwd, String.valueOf(bean.getData().getUserinfo().getId()));
//                                        getPerInfo(String.valueOf(bean.getData().getUserinfo().getId()));
                                        app.setParam(USER_ISREFRESH_PERSONINFO, USER_FRESH_NO);
                                        LoginBean.DataBean.UserinfoBean userinfo = bean.getData().getUserinfo();
                                        if (userinfo != null) {
                                            ContextProperties.writeProperties(mContext, userinfo);
                                            EventBus.getDefault().postSticky(userinfo);
                                            initPersonData();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void closeDialog() {
        if (dialogEditName != null && dialogEditName.isShowing()) {
            dialogEditName.dismiss();
        }
        if (dialogSelectSex != null && dialogSelectSex.isShowing()) {
            dialogSelectSex.dismiss();
        }
    }


    private void initPersonData() {
        String headImgUrl = userinfo.getAvatar();
        String nickName = userinfo.getNickname();
        String sex = userinfo.getSex();
        String phone = userinfo.getMobile();
//        tvJingbi.setText(String.valueOf(userinfo.getScore()));
//        tvMoney.setText(String.valueOf("¥"+userinfo.getMoney()));
        if (!TextUtils.isEmpty(headImgUrl)) {
            LoadImageUtil.loadImg(headImgUrl, iv_headimg, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
        }
        if (!TextUtils.isEmpty(nickName)) {
            tv_nickname.setText(nickName);
            if ("未设定".equals(nickName)) {
                rlyt_nickname_point.setVisibility(View.VISIBLE);
            } else {
                rlyt_nickname_point.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(sex)) {
            tv_sex.setText(sex);
            if ("未知".equals(sex)) {
                rlyt_sex_point.setVisibility(View.VISIBLE);
            } else {
                rlyt_sex_point.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(phone)) {
            tv_phone.setText(phone);
        }
    }

    /**
     * <br>
     * 功能简述:裁剪图片方法实现----------------------相机 <br>
     * 功能详细描述: <br>
     * 注意:
     *
     * @param uri
     */
    private void cameraCropImageUri(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        // if (mIsKitKat) {
        // intent.putExtra("return-data", true);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // } else {
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, SET_PICTURE);
    }

    /**
     * 初始化图片路径
     */
    private void initFile() {
        File directory = new File(ACCOUNT_DIR);
        File imagepath = new File(IMGPATH);
        try {
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    directory.mkdirs();
                }
            }
            if (!imagepath.exists()) {
                if (!imagepath.mkdir()) {
                    imagepath.mkdirs();
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        fileone = new File(IMGPATH, IMAGE_FILE_NAME);
        filetwo = new File(IMGPATH, TMP_IMAGE_FILE_NAME);

        try {
            if (!fileone.exists() && !filetwo.exists()) {
                fileone.createNewFile();
                filetwo.createNewFile();
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @Event(R.id.rlyt_back)
    private void goback(View view) {
        finish();
    }

    @Event(R.id.rlyt_edit_pwd)
    private void gotoedidpwd(View view) {
        startActivity(new Intent(mContext, EditPwdActivity.class));
    }

    private void initView() {
//        rlytMoney = findViewById(R.id.rlyt_money);
//        tvMoney = findViewById(R.id.tv_money);
//        rlytJingbi = findViewById(R.id.rlyt_jingbi);
//        tvJingbi = findViewById(R.id.tv_jingbi);
    }
}
