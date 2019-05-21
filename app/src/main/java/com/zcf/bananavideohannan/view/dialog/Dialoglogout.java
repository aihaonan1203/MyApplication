package com.zcf.bananavideohannan.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gaotai.baselib.util.CompleteQuit;
import com.zcf.bananavideohannan.MainActivity;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.ContextProperties;

import java.util.List;

/**
 * 退出登录
 */
public class Dialoglogout extends AlertDialog implements View.OnClickListener {
    private Activity mContext;

    public Dialoglogout(Activity context) {
        super(context);
        this.mContext = context;
    }

    private TextView tv_cancle;
    private TextView tv_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_out);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        tv_cancle = findViewById(R.id.tv_cancle);
        tv_enter = findViewById(R.id.tv_enter);

        tv_cancle.setOnClickListener(this);
        tv_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_enter:
                ContextProperties.clearRem(mContext);

                List<Activity> activityList = CompleteQuit.getInstance().getActivityList();
                for(Activity activity : activityList){
                    if(activity instanceof MainActivity){

                    }else{
                        activity.finish();
                    }
                }


//                CompleteQuit.getInstance().clearActivityOtherMain();

//                mContext.startActivity(new Intent(mContext, MainActivity.class));
                break;
        }
    }
}
