package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by yanjin on 2017/8/22.
 * <p>
 * 处理图片浏览器快速滑动异常
 */
public class ViewPagerCustom extends ViewPager {
    public ViewPagerCustom(Context context) {
        super(context);
    }

    public ViewPagerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        } catch (IllegalArgumentException e) {
            Log.e("ImageOriginPager-error", "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException e) {
            Log.e("ImageOriginPager-error", "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }
}
