package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * 自定义textview 旋转45
 */
public class RotateTextView extends android.support.v7.widget.AppCompatTextView {


    public RotateTextView(Context context) {
        super(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(310, getMeasuredWidth() / 3, getMeasuredHeight() / 3);
        super.onDraw(canvas);
    }

}
