package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by yanjin on 2017/7/24.
 */
public class CustomScrollView extends PullToRefreshScrollView {
    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public CustomScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
