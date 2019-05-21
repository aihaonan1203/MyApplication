package com.zcf.bananavideohannan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.TixianBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.ToastUtils;
import com.zcf.bananavideohannan.util.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WithdrawalActivity extends MyBaseActivity {

    private ImageView ivBack;
    private RecyclerView rvData;
    private List<TixianBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
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
        Disposable disposable=Network.getVideoApi().withdrawlist(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString())
                .compose(this.<CommonlyBean<TixianBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<TixianBean>>() {
                    @Override
                    public void accept(CommonlyBean<TixianBean> beanCommonlyBean) throws Exception {
                        ToastUtils.dismiss();
                        if (beanCommonlyBean.getCode()==200){
                            data = beanCommonlyBean.getData();
                            if (data!=null&&data.size()>0){
                                Collections.sort(data, new Comparator<TixianBean>() {
                                    @Override
                                    public int compare(TixianBean o1, TixianBean o2) {
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

    private MyAdapter adapter;
    private void initAdapter() {
        if (adapter==null){
            adapter=new MyAdapter();
            rvData.setLayoutManager(new LinearLayoutManager(WithdrawalActivity.this));
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



    private class  MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(WithdrawalActivity.this).inflate(R.layout.tixian_item,parent,false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TixianBean bean = data.get(position);
            String s = Utils.stampToDate(bean.getCreate_time());
            holder.txt1.setText("提现时间:"+s);
            holder.txt2.setText("提现账号:"+bean.getAccount());
            holder.txt3.setText("提现金额:"+bean.getMoney());
            String status;
            if (bean.getOrder_status().equals("0")) {
                status="审核中";
            }else if (bean.getOrder_status().equals("1")){
                status="通过";
            }else {
                status="拒绝";
            }
            holder.txt4.setText("提现状态:"+status);
            if (bean.getSupportpay().equals("1")){
                holder.icon.setImageResource(R.drawable.weixin);
            }else {
                holder.icon.setImageResource(R.drawable.zhifubao);
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView txt1;
            public TextView txt2;
            public TextView txt3;
            public TextView txt4;
            public ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);
                initView(itemView);
            }

            private void initView(View view) {
                txt1=view.findViewById(R.id.tv_txt1);
                txt2=view.findViewById(R.id.tv_txt2);
                txt3=view.findViewById(R.id.tv_txt3);
                txt4=view.findViewById(R.id.tv_txt4);
                icon=view.findViewById(R.id.iv_icon);
            }


        }
    }
}
