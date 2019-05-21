package com.zcf.bananavideohannan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.gaotai.baselib.util.SharePreference;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.master.permissionhelper.PermissionHelper;
import com.zcf.bananavideohannan.activity.login.LoginActivity;
import com.zcf.bananavideohannan.adapter.GTPictureDetailAdapter;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.ImageBean;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.MyResponseBody;
import com.zcf.bananavideohannan.bean.WelcomeBean;
import com.zcf.bananavideohannan.bll.LoginBll;
import com.zcf.bananavideohannan.domain.AdDomain;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.util.RxTimerUtil;
import com.zcf.bananavideohannan.util.SPUtils;
import com.zcf.bananavideohannan.util.ToastUtils;
import com.zcf.bananavideohannan.view.ViewPagerCustom;
import com.zcf.bananavideohannan.view.dialog.DialogEditName;
import com.zcf.bananavideohannan.view.dialog.DialogPwd;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 欢迎页
 */
@ContentView(R.layout.activity_welcome)
public class WelComeActivity extends MyBaseActivity {
    /**
     * 图片展示控件
     */
    private ViewPagerCustom pic_viewpager;
    private TextView tv_goon;
    private TextView tv_less_second;
    private PermissionHelper permissionHelper;
    /**
     * radioGroup滑动point
     */
    private RadioGroup rg_point;
    private Button btn_gotomain;

    private ImageView rlyt_bg;

    private RelativeLayout rlyt_welcome;
    private boolean isStop = false;
    private List<AdDomain> top_ad_data;
    private GTPictureDetailAdapter adapter;
    private final int HANDLER_GOTO_MAIN = 1;
    private final int HANDLER_GOTO_Login = 2;
    private final int HANDLER_AUTO_PLAY = 3;

    private Runnable runableTask;
    private int current_lessTime;

    @SuppressLint("HandlerLeak")
    private Handler handelr = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_GOTO_MAIN) {
                gotoMain();
            } else if (msg.what == HANDLER_GOTO_Login) {
                gotoLogin();
            } else if (msg.what == HANDLER_AUTO_PLAY) {
                if (current_lessTime == 0) {
                    tv_less_second.setText("X");
                } else {
                    tv_less_second.setText(String.valueOf(current_lessTime) + "s");
                }
            }
        }
    };
    private ImageBean bean1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_gotomain = findViewById(R.id.btn_gotomain);
        tv_goon = findViewById(R.id.tv_goon);
        tv_less_second = findViewById(R.id.tv_less_second);
        btn_gotomain.setVisibility(View.GONE);
        rlyt_bg = findViewById(R.id.rlyt_bg);
        rlyt_welcome = findViewById(R.id.rlyt_welcome);
        pic_viewpager = findViewById(R.id.pic_viewpager);
        rg_point = findViewById(R.id.rg_point);
        setListeners();
//        if (isFirstOpen()) {
//            rlyt_bg.setVisibility(View.GONE);
//            rlyt_welcome.setVisibility(View.VISIBLE);
//            loadWelcomeBG(ACTION_URL_GET_ADS);
//        } else {
        rlyt_bg.setVisibility(View.VISIBLE);
        rlyt_welcome.setVisibility(View.GONE);
//        }
        if (!SPUtils.getBoolean(ContextProperties.REM_GROUP_PASSWORD,false)){
            alert_edit();
        }else {
            downImage();
        }

    }

    public void alert_edit(){
//        final EditText et = new EditText(this);
        final DialogPwd dialogPwd=new DialogPwd(this);
        dialogPwd.setCanceledOnTouchOutside(true);
        dialogPwd.setView(new EditText(mContext));
        dialogPwd.setOnClick(new DialogPwd.onClick() {
            @Override
            public void onAffirm(String name) {
                if (name.isEmpty()){
                    Toast.makeText(mContext, "不得为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Disposable disposable=Network.getVideoApi().password(name)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<MyResponseBody>() {
                                    @Override
                                    public void accept(MyResponseBody responseBody) throws Exception {
                                        if (responseBody.getCode()==200){
                                            dialogPwd.dismiss();
                                            SPUtils.saveBoolean(ContextProperties.REM_GROUP_PASSWORD,true);
                                            downImage();
                                        }else {
                                            dialogPwd.dismiss();
                                            ToastUtils.show(WelComeActivity.this,"集团密码错误,3秒后再试！");
                                            RxTimerUtil.timer(3000, new RxTimerUtil.IRxNext() {
                                                @Override
                                                public void doNext(long number) {
                                                    ToastUtils.dismiss();
                                                    alert_edit();
                                                }
                                            });
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        ToastUtils.show(WelComeActivity.this,"集团密码错误,3秒后再试！");
                                        RxTimerUtil.timer(3000, new RxTimerUtil.IRxNext() {
                                            @Override
                                            public void doNext(long number) {
                                                ToastUtils.dismiss();
                                                dialogPwd.dismiss();
                                                alert_edit();
                                            }
                                        });
                                    }
                                });
            }

            @Override
            public void onCancle(String name) {
                finish();
            }
        });
        dialogPwd.show();
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                }).show();
    }


    private void downImage() {
        final ImageBean bean=new ImageBean();
        final String launch = SPUtils.getString("launch", "");
        if (!launch.isEmpty()){
            bean1 = GsonUtil.parseJsonWithGson(launch, ImageBean.class);
        }
        Network.getVideoApi().launch()
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<MyResponseBody>() {
                    @Override
                    public boolean test(MyResponseBody responseBody) throws Exception {
                        if (responseBody.getCode()==200){
                            if (bean1==null||!bean1.getUrl().equals(responseBody.getData())){
                                bean.setUrl(responseBody.getData());
                                return true;
                            }
                        }
                        return false;
                    }
                }).flatMap(new Function<MyResponseBody, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(final MyResponseBody responseBody) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<File>() {
                            @Override
                            public void subscribe(ObservableEmitter<File> e) throws Exception {
                                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                                e.onNext(Glide.with(mContext)
                                        .load(responseBody.getData())
                                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                        .get());
                                e.onComplete();
                            }
                        });
                    }
                }).subscribeOn(Schedulers.newThread())
                .map(new Function<File, Boolean>() {
                    @Override
                    public Boolean apply(File file) throws Exception {
                        bean.setPath(file.getPath());
                        return false;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Gson gson=new Gson();
                        String s = gson.toJson(bean);
                        SPUtils.saveString("launch",s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getpermission();
                    }

                    @Override
                    public void onComplete() {
                        getpermission();
                    }
                });

//        Disposable d=Observable.create(new ObservableOnSubscribe<File>() {
//            @Override
//            public void subscribe(ObservableEmitter<File> e) throws Exception {
//                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
//                e.onNext(Glide.with(mContext)
//                        .load("")
//                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get());
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(File file) throws Exception {
//                        //获取到下载得到的图片，进行本地保存
//                        File pictureFolder = Environment.getExternalStorageDirectory();
//                        //第二个参数为你想要保存的目录名称
//                        File appDir = new File(pictureFolder, "your_picture_save_path");
//                        if (!appDir.exists()) {
//                            appDir.mkdirs();
//                        }
//                        String fileName = System.currentTimeMillis() + ".jpg";
//                        File destFile = new File(appDir, fileName);
//                        //把gilde下载得到图片复制到定义好的目录中去
//                        copy(file, destFile);
//
//                        // 最后通知图库更新
//                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                                Uri.fromFile(new File(destFile.getPath()))));
//                    }
//                });
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListeners() {
        pic_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == top_ad_data.size() - 1) {
                    tv_goon.setVisibility(View.GONE);
                    btn_gotomain.setVisibility(View.VISIBLE);
                } else {
                    tv_goon.setVisibility(View.VISIBLE);
                    btn_gotomain.setVisibility(View.GONE);
                }
                RadioButton rbtn = (RadioButton) rg_point.getChildAt(position);
                rbtn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btn_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharePreference().writeConfig(mContext, "app_config", "config.opend", "1");
                gotoMain();
            }
        });

        tv_goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharePreference().writeConfig(mContext, "app_config", "config.opend", "1");
                gotoMain();
            }
        });
        tv_less_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = tv_less_second.getText().toString();
                if (!TextUtils.isEmpty(s) && s.equals("X")) {
                    gotoMain();
                }
            }
        });

        tv_less_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("X".equals(tv_less_second.getText().toString())) {
                    gotoMain();
                }
            }
        });
    }

    private void gotoTheadMain(int second) {
//        runableTask = new Runnable() {
//            @Override
//            public void run() {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        if (isLoginSuccess) {
//                            handelr.sendEmptyMessage(HANDLER_GOTO_MAIN);
//                        } else {
//                            handelr.sendEmptyMessage(HANDLER_GOTO_Login);
//                        }
//                    }
//                }.start();
//            }
//        };
//        handelr.postDelayed(runableTask, second * 1000);
        if (bean1!=null){
            Glide.with(WelComeActivity.this).load(new File(bean1.getPath())).into(rlyt_bg);
        }
        tv_less_second.setVisibility(View.VISIBLE);
        runableTask = new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 3; i >= 0; i--) {
                            current_lessTime = i;
                            handelr.sendEmptyMessage(HANDLER_AUTO_PLAY);
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        };
        handelr.postDelayed(runableTask, second * 300);
    }

    /**
     * 图片集展示
     */
    private void initAdapter() {
        tv_goon.setVisibility(View.VISIBLE);
//        adapter = new GTPictureDetailAdapter(getSupportFragmentManager(), top_ad_data);
        pic_viewpager.setAdapter(adapter);
        initGroup();
    }

    private void loadWelcomeBG(String url) {
        OkGo.<String>post(url).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response != null && 200 == response.code()) {
                            WelcomeBean bean = GsonUtil.parseJsonWithGson(response.body(), WelcomeBean.class);
                            if (bean != null) {
                                top_ad_data = bean.getData();
                                if (!listIsNull(top_ad_data)) {
                                    initAdapter();
                                } else {
                                    gotoTheadMain(2);
//                            gotoMain();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 是否第一次加载
     *
     * @return
     */
    private boolean isFirstOpen() {
        boolean result = true;
        // 读取配置文件，如果第一次打开，则加载应用幻灯片
        String opend = new SharePreference().readConfig(mContext, "app_config", "config.opend");
        if (opend != null) {
            if ("1".equals(opend)) {
                // 非首次打开
                result = false;
            }
        }
        return result;
    }

    /**
     * 动态显示radioGroup点
     */
    private void initGroup() {
        rg_point.removeAllViews();
        for (int i = 0; i < top_ad_data.size(); i++) {
            RadioButton rbtn = new RadioButton(mContext);
            rbtn.setButtonDrawable(null);
            rbtn.setBackgroundResource(R.drawable.radiobutton_ischecked);
            rbtn.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            rbtn.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            rg_point.addView(rbtn);
        }
        RadioButton rbtn = (RadioButton) rg_point.getChildAt(0);
        rbtn.setChecked(true);
    }

    private void getpermission() {
        permissionHelper = new PermissionHelper(this, new String[]{
                android.Manifest.permission.CAMERA
//                , android.Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 100);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
//                if (!isFirstOpen()) {
////                    gotoMain();
                    autoLogin();
//                } else {
//
//                }
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                getpermission();
            }

            @Override
            public void onPermissionDenied() {
                getpermission();
            }

            @Override
            public void onPermissionDeniedBySystem() {
                showMissingPermissionDialog("相机,定位,联系人");
            }
        });
    }

    private void gotoMain() {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoLogin() {
        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showMissingPermissionDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage("当前应用缺少-" + s + "-权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                finish();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    // 自动登录
    private void autoLogin() {
        String uid = ContextProperties.readRemember(mContext, ContextProperties.REM_UID);
        String password = ContextProperties.readRemember(mContext, ContextProperties.REM_PASSWORD);
        String account = ContextProperties.readRemember(mContext, ContextProperties.REM_ACCOUNUT);

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(uid)) {
            httpLogin(account, password);
            // 从文件恢复到内存
            ContextProperties.reLoadLogin(mContext);
        } else {
            ContextProperties.clearRem(mContext);
            RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
                @Override
                public void doNext(long number) {
                    gotoTheadMain(0);
                }
            });
        }
    }

    boolean isLoginSuccess = false;

    private void httpLogin(final String account, final String pwd) {
//        ProgressDialogUtil.showDialog(mContext, "自动登陆中", false);
        OkGo.<String>post(ACTION_URL_LOGIN).tag(this).isMultipart(true)
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
                                        isLoginSuccess = true;
                                        ContextProperties.remeberCache(mContext, account, pwd, String.valueOf(bean.getData().getUserinfo().getId()));
                                        EventBus.getDefault().postSticky(bean.getData().getUserinfo());
//                                        getPerInfo(String.valueOf(bean.getUid()));
                                    } else {
                                        ContextProperties.clearRem(mContext);
                                    }
                                }
                                RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
                                    @Override
                                    public void doNext(long number) {
                                        gotoTheadMain(0);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            ContextProperties.clearRem(mContext);
                            e.printStackTrace();
                        }
//                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ProgressDialogUtil.dismiss();
                        ContextProperties.clearRem(mContext);
                        gotoTheadMain(1);
                        super.onError(response);
                    }
                });
    }

    private void getPerInfo(final String uid) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginBll loginBll = new LoginBll(mContext);
                    loginBll.getPersonInfo(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread = null;
    }

    private void remeberCache(String account, String pwd, String uid) {
        ContextProperties.remeberCache(mContext, account, pwd, uid);
    }
}
