package com.zcf.bananavideohannan.activity.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nanchen.compresshelper.CompressHelper;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.WithdrawalActivity;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.TixianBean;
import com.zcf.bananavideohannan.bean.Ttt;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.ToastUtils;
import com.zcf.bananavideohannan.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

@SuppressLint("Registered")
public class MyMoneyActivity extends MyBaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private RecyclerView rvData;
    private List<Ttt.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_money);
        initView();
        getDataForNet();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataForNet() {
        ToastUtils.show(this,"正在加载...");
        Disposable disposable=Network.getTopUpApi().moneylog(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString())
                .compose(this.<Ttt>applySchedulers())
                .subscribe(new Consumer<Ttt>() {
                    @Override
                    public void accept(Ttt ttt) throws Exception {
                        ToastUtils.dismiss();
                        if (ttt.getCode()==200){
                            data = ttt.getData();
                            if (data!=null&&data.size()>0){
                                Collections.sort(data, new Comparator<Ttt.DataBean>() {
                                    @Override
                                    public int compare(Ttt.DataBean o1, Ttt.DataBean o2) {
                                        return o2.getId()-o1.getId();
                                    }
                                });
                                initAdapter();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                        ToastUtils.dismiss();
                    }
                });
    }

    private MyMoneyActivity.MyAdapter adapter;
    private void initAdapter() {
        if (adapter==null){
            adapter=new MyMoneyActivity.MyAdapter();
            rvData.setLayoutManager(new LinearLayoutManager(MyMoneyActivity.this));
            rvData.addItemDecoration(new RecycleViewDivider(
                    mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.main_bq_color)));
            rvData.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        rvData = findViewById(R.id.rv_data);
    }

    @Override
    public void onClick(View v) {

    }


    private class  MyAdapter extends RecyclerView.Adapter<MyMoneyActivity.MyAdapter.ViewHolder>{


        @NonNull
        @Override
        public MyMoneyActivity.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(MyMoneyActivity.this).inflate(R.layout.tixian_item2,parent,false);
            return new MyMoneyActivity.MyAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MyMoneyActivity.MyAdapter.ViewHolder holder, int position) {
            Ttt.DataBean bean = data.get(position);
            String s = Utils.stampToDate(bean.getCreate_time());
            holder.txt1.setText("到账时间:"+s);
            holder.txt3.setText("+"+bean.getMoney());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView txt1;
            public TextView txt3;

            public ViewHolder(View itemView) {
                super(itemView);
                initView(itemView);
            }

            private void initView(View view) {
                txt1=view.findViewById(R.id.tv_txt1);
                txt3=view.findViewById(R.id.tv_txt3);
            }


        }
    }
}
