package com.gaotai.baselib.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.CompleteQuit;
import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.view.dialog.ToastUtil;

import org.xutils.x;

/**
 * 基础Activity 
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 页面传递参数Key:页面标题
     */
    public static String EXTRA_TITLE = "extraTitle";

    /**
     * 页面传递参数Key:自定义标签
     */
    public static String EXTRA_TAG = "extraTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompleteQuit.getInstance().addActivity(this);
        x.view().inject(this);
    }

    /**
     * 当前操作时间
     */
    private  long operatetime = DcDateUtils.now().getTime();
    /**
     * 记录当前触摸操作的时间
     */
    protected void setTouchOperatetime()
    {
        if(DcDateUtils.now().getTime() - operatetime > 1000)
        {
            operatetime = DcDateUtils.now().getTime();
            DcAndroidContext.getInstance().setParam(DcAndroidConsts.OPERATETIME,operatetime);
        }
    }

    /**
     * 跳转界面
     *
     * @param bundle 参数集合
     * @param cls   Activity
     */
    public void pushActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        //Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面
     *
     * @param title 导航栏标题
     * @param cls   Activity
     */
    public void pushActivity(String title, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 跳转界面
     *
     * @param title 导航栏标题
     * @param tag   自定义标签
     * @param cls   Activity
     */
    public void pushActivity(String title, String tag, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_TAG, tag);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        CompleteQuit.getInstance().removeActivity(this);
        super.onDestroy();
    }



    //**************** Android M Permission (Android 6.0权限控制代码封装)
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;

    /**
     * 申请权限返回接口
     */
    public interface PermissionCallback{
        /**
         * 成功获取权限
         */
        void hasPermission();

        /**
         * 获取权限失败
         */
        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions){
        if(permissions == null || permissions.length == 0)return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            //permission has not been granted.
            requestPermission(permissionDes,permissionRequestCode,permissions);
        }

    }

    /**
     * 直接申请权限
     * @param permissions
     * @return
     */
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * API 23 及以上 申请权限
     * @param permissionDes
     * @param requestCode
     * @param permissions
     */
    private void requestPermission(String permissionDes,final int requestCode,final String[] permissions){
        if(shouldShowRequestPermissionRationale(permissions)){
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    }).show();

        }else{
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            }else{
                ToastUtil.toastLong(this,"暂无权限执行相关操作！");
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************


    //********************** 使用示例 ****************************************
//    performCodeWithPermission("XX App请求访问相机权限",new BaseAppCompatActivity.PermissionCallback() {
//        @Override
//        public void hasPermission() {
//            //执行打开相机相关代码
//        }
//        @Override
//        public void noPermission() {
//        }
//    }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);


//    /**
//     * Android M运行时权限请求封装
//     * 在Fragment中使用，直接在自己的BaseFragment写个方法调用此Activity的方法即可。
//     * @param permissionDes 权限描述
//     * @param runnable 请求权限回调
//     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
//     */
//    public void performCodeWithPermission(@NonNull String permissionDes,BaseAppCompatActivity.PermissionCallback runnable,@NonNull String... permissions){
//        if(getActivity()!=null && getActivity() instanceof BaseAppCompatActivity){
//            ((BaseActivity) getActivity()).performCodeWithPermission(permissionDes,runnable,permissions);
//        }
//    }
}
