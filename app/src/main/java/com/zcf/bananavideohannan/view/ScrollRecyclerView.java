package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollRecyclerView extends RecyclerView {

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float lastX, lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercept = super.onInterceptTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 只要横向大于竖向，就拦截掉事件。
                // 部分机型点击事件（slopx==slopy==0），会触发MOVE事件。
                // 所以要加判断(slopX > 0 || sloy > 0)
                float slopX = Math.abs(e.getX() - lastX);
                float slopY = Math.abs(e.getY() - lastY);
                //  Log.log("slopX=" + slopX + ", slopY="  + slopY);
                if ((slopX > 0 || slopY > 0) && slopX >= slopY) {
                    requestDisallowInterceptTouchEvent(true);
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        // Log.log("intercept"+e.getAction()+"=" + intercept);
        return intercept;
    }
}