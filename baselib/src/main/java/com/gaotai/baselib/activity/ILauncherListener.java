package com.gaotai.baselib.activity;

import com.gaotai.baselib.activity.bean.ILauncher;


/**
 * <b>BaseActivity处理结果回调接口</b>
 *
 */
public interface ILauncherListener {
   /**
    * 处理成功时的回调
    * @param launcher
    * @param reqcode
    * @param data
    */
   void onSuccess(ILauncher launcher,int reqcode,String data);
   /**
    * 处理失败时的回调
    * @param launcher
    * @param reqcode
    * @param message
    */
   void onError(ILauncher launcher,int reqcode,String message);
}
