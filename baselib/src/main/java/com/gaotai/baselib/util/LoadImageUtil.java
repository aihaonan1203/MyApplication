package com.gaotai.baselib.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2017/5/10.
 */
public class LoadImageUtil {

    /**
     * 图片加载设置
     *
     * @param resid 自定义默认图片
     */
    public static DisplayImageOptions getImageOptions(final int resid) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(resid)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(resid)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resid)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))// 设置成圆角图片
                .build();// 创建DisplayImageOptions对象
        return options;
    }

    /**
     * 图片加载设置
     */
    public static DisplayImageOptions getImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(10)
                .cacheOnDisc(true) //
                .cacheInMemory(true) // default 不缓存至内存
                .cacheOnDisk(true) // default  缓存至手机SDCard
                .imageScaleType(ImageScaleType.EXACTLY)// default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .considerExifParams(true).handler(new Handler()) // default
                .build();
        return options;
    }


    /**
     * 加载图片
     *
     * @param imigUrl 图片地址
     * @param imgv    图片显示空间
     * @param options 图片加载设置
     * @param resid   默认显示图片
     */
    public static void loadImg(String imigUrl, final ImageView imgv, DisplayImageOptions options, final int resid) {
        ImageLoader.getInstance().displayImage(imigUrl, imgv, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imgv.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }

    /**
     * 加载图片
     *
     * @param imigUrl  图片地址
     * @param imgv     图片显示空间
     * @param options  图片加载设置
     * @param resid    默认显示图片
     * @param position 图片的tag标识
     */
    public static void loadImg(final String imigUrl, final ImageView imgv, DisplayImageOptions options, final int resid, final int position) {
        ImageLoader.getInstance().displayImage(imigUrl, imgv, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (view.getTag().equals(position + imigUrl)) {
                    imgv.setImageBitmap(bitmap);
                } else {
                    imgv.setImageResource(resid);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }

    /**
     * 根据图片地址获取原始图片地址
     */
    public static String getOriginalPic(String picUrl) {
        /* 去掉图片url里最后一个.(包含)和_(包含)之间的字符串*/
        StringBuffer newUrl = new StringBuffer(picUrl);
        int last_index = picUrl.lastIndexOf("_");
        int lastPointIndex = picUrl.lastIndexOf(".");
        String substr = picUrl.substring(last_index, lastPointIndex);
        String start = picUrl.substring(0, last_index);
        String end = picUrl.substring(lastPointIndex, picUrl.length());
        if (isContainKey(substr)) {
            substr = "_512";
            newUrl = new StringBuffer();
            newUrl.append(start).append(substr).append(end);
        }
        return newUrl.toString();
    }

    private static boolean isContainKey(String str) {
        String[] STRS_TEMP = {"_32", "_64", "_128", "_256", "_512"};
        for (int i = 0; i < STRS_TEMP.length; i++) {
            String temp = STRS_TEMP[i];
            if (str.equals(temp)) {
                return true;
            }
        }
        return false;
    }
}
