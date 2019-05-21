package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yanjin on 2017/6/5.
 * 禁止滑动viewpager
 */
public class ViewPagerNoScroll extends ViewPager {

    /**
     * 是否禁止滑动
     */
    private boolean noScroll = true;

    public ViewPagerNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerNoScroll(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * 觸摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        try {
            // 如果禁止滑動，返回false
            if (noScroll)
                return false;
            else
                return super.onTouchEvent(arg0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            // 如果禁止滑動，返回false
            if (noScroll)
                return false;
            else
                return super.onInterceptTouchEvent(arg0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}

