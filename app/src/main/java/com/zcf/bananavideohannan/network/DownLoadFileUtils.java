package com.zcf.bananavideohannan.network;

import android.content.Context;
import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;

import java.io.File;

public class DownLoadFileUtils {
    private static String mBasePath; //本地文件存储的完整路径  /storage/emulated/0/book/恰似寒光遇骄阳.txt
    /**
     *
     * @param context 上下文
     * @param fileUrl 下载完整url
     * @param destFileDir  SD路径
     * @param destFileName  文件名
     * @param mFileRelativeUrl 下载相对地址
     * （我们从服务器端获取到的数据都是相对的地址）例如： "filepath": "/movie/20180511/1526028508.txt"
     */
    public static void downloadFile(Context context, String fileUrl, String destFileDir, String destFileName, String mFileRelativeUrl, final MyFileCallback callback){
        String  mDestFileName =destFileName+mFileRelativeUrl.substring(mFileRelativeUrl.lastIndexOf("."),mFileRelativeUrl.length());
        OkGo.<File>get(fileUrl).tag(context).execute(new FileCallback(destFileDir,mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                mBasePath=response.body().getAbsolutePath();
                callback.onSuccess(mBasePath);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                callback.onError();
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                float  dLProgress= progress.fraction;
            }
        });
    }

    //拼接一个本地的完整的url 供下载文件时传入一个本地的路径
    private static final String mSDPath = Environment.getExternalStorageDirectory().getPath();
    //分类别路径
    private static String mClassifyPath=null;
    public static String customLocalStoragePath(String differentName){
        File basePath=new File(mSDPath); // /storage/emulated/0
        mClassifyPath=mSDPath+"/"+differentName+"/";  //如果传来的是 book 拼接就是 /storage/emulated/0/book/
        //如果传来的是game  那拼接就是 /storage/emulated/0/game/
        if(!basePath.exists()){
            basePath.mkdirs();
            System.out.println("文件夹创建成功");
        }

        return mClassifyPath;
    }


    //截取一个文件加载显示时传入的一个本地完整路径
    public static String subFileFullName(String fileName,String fileUrl){
        String cutName=fileName+fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.length());  //这里获取的是  恰似寒光遇骄阳.txt
        return cutName;
    }


    public interface MyFileCallback{
        void onSuccess(String baseUrl);
        void onError();
    }
}




