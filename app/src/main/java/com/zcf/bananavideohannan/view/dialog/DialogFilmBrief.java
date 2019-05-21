package com.zcf.bananavideohannan.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.bean.ReboBean;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.AutoLinefeedLayout;

import java.util.Map;

public class DialogFilmBrief extends AlertDialog {
    private LinearLayout llyt_main;
    private TextView tv_close;
    private TextView tv_film_brief;
    private TextView tv_filmName;
    private TextView tv_dialog_playnum;
    private AutoLinefeedLayout autoline_bq;
    private Context mContext;
    private int height;// 设置上边距
    private ReboBean.DataBean videoModel;
    private Map<Object,String> label;

    public DialogFilmBrief(Context context, int height, ReboBean.DataBean videoModel, Map<Object,String> label) {
        super(context);
        this.mContext = context;
        this.height = height;
        this.videoModel = videoModel;
        this.label=label;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_film_jianjie);
        tv_close = findViewById(R.id.tv_close);
        autoline_bq = findViewById(R.id.autoline_bq);
        tv_film_brief = findViewById(R.id.tv_film_brief);
        tv_dialog_playnum = findViewById(R.id.tv_dialog_playnum);
        tv_filmName = findViewById(R.id.tv_filmName);
        llyt_main = findViewById(R.id.llyt_main);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llyt_main.getLayoutParams();
        params.setMargins(0, height, 0, 0);//4个参数按顺序分别是左上右下
        llyt_main.setLayoutParams(params);

        if (videoModel != null) {
            if (videoModel.getLabel() != null) {
                String[] labels = videoModel.getLabel().split(",");
                for (int j = 0; j < labels.length; j++) {
                    final String s = label.get(Integer.parseInt(labels[j]));
                    TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.childview_film_label, null);
                    textView.setText(s);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.toastShort(mContext, s);
                        }
                    });
                    autoline_bq.addView(textView);
                }
            }

            tv_film_brief.setText(videoModel.getContent());
            tv_filmName.setText(videoModel.getName());
            tv_dialog_playnum.setText(Utils.getStrPlayNum(videoModel.getViews()));
        }
    }

}
