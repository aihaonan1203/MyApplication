package com.zcf.bananavideohannan.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gaotai.baselib.util.AndroidUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.GongGaoBean;

/**
 * 删除-底部弹出dialog
 */
public class DialogDefGonggao extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    GongGaoBean.DataBean data;

    public DialogDefGonggao(Context context, GongGaoBean.DataBean data) {
        super(context);
        this.mContext = context;
        this.data = data;
    }

    private TextView tv_dialog_title;
    private TextView tv_content;
    private TextView tv_text;
    private Button btn_know;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_def_gongg);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        tv_dialog_title = findViewById(R.id.tv_dialog_title);
        tv_content = findViewById(R.id.tv_content);
        tv_text = findViewById(R.id.tv_text);
        btn_know = findViewById(R.id.btn_know);

        if (data != null) {
            tv_dialog_title.setText(data.getTitle());
            tv_text.setText(data.getNotice_content());
            if (!TextUtils.isEmpty(data.getLink_url())) {
                SpannableStringBuilder textSpanned4 = new SpannableStringBuilder(data.getLink_url());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        AndroidUtil.openBrowser(mContext, data.getLink_url());
                    }
                };
                textSpanned4.setSpan(clickableSpan,
                        0, data.getLink_url().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //注意：此时必须加这一句，不然点击事件不会生效
                tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                tv_content.setText(textSpanned4);
            }
        }

        btn_know.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_know:
                dismiss();
                break;
        }
    }

}
