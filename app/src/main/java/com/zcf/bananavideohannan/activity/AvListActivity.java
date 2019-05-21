package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.ActorAdapter;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.network.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AvListActivity extends MyBaseActivity implements View.OnClickListener {

    private RadioGroup rg_video_type;

    private RadioGroup rg_zb;

    private RadioButton rb_renq;

    private RadioButton rb_nums;

    private RadioButton rb_a;

    private RadioButton rb_b;

    private RadioButton rb_c;

    private RadioButton rb_d;

    private RadioButton rb_e;

    private RadioButton rb_f;

    private RadioButton rb_g;

    private RadioButton rb_h;

    private RadioButton rb_i;
    private RadioButton rb_all;

    private GridView gv_actors;
    private ImageView iv_back;

    private String order = "renqi";
    private String cup = "";

    private ActorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_av_list);
        rg_video_type = findViewById(R.id.rg_video_type);
        rg_zb = findViewById(R.id.rg_zb);
        rb_renq = findViewById(R.id.rb_renq);
        rb_nums = findViewById(R.id.rb_nums);
        iv_back = findViewById(R.id.iv_back);

        rb_a = findViewById(R.id.rb_a);
        rb_b = findViewById(R.id.rb_b);
        rb_c = findViewById(R.id.rb_c);
        rb_d = findViewById(R.id.rb_d);
        rb_e = findViewById(R.id.rb_e);
        rb_f = findViewById(R.id.rb_f);
        rb_g = findViewById(R.id.rb_g);
        rb_h = findViewById(R.id.rb_h);
        rb_i = findViewById(R.id.rb_i);
        rb_all = findViewById(R.id.rb_all);
        gv_actors = findViewById(R.id.gv_actors);

        rg_video_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case 0:
                        rb_renq.setChecked(true);
                        break;
                    case 1:
                        rb_nums.setChecked(true);
                        break;
                }
            }
        });

        rg_zb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case 0:
                        rb_a.setChecked(true);
                        break;
                    case 1:
                        rb_b.setChecked(true);
                        break;
                    case 2:
                        rb_c.setChecked(true);
                        break;
                    case 3:
                        rb_d.setChecked(true);
                        break;
                    case 4:
                        rb_e.setChecked(true);
                        break;
                    case 5:
                        rb_f.setChecked(true);
                        break;
                    case 6:
                        rb_g.setChecked(true);
                        break;
                    case 7:
                        rb_h.setChecked(true);
                        break;
                    case 8:
                        rb_i.setChecked(true);
                        break;
                }
            }
        });

        rb_renq.setOnClickListener(this);
        rb_nums.setOnClickListener(this);
        rb_a.setOnClickListener(this);
        rb_b.setOnClickListener(this);
        rb_c.setOnClickListener(this);
        rb_d.setOnClickListener(this);
        rb_e.setOnClickListener(this);
        rb_f.setOnClickListener(this);
        rb_g.setOnClickListener(this);
        rb_h.setOnClickListener(this);
        rb_i.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rb_all.setOnClickListener(this);

        getNyData("","1","1");
//        getData(order, cup);
    }

    List<AVBean> data;
    List<AVBean> all_data;

    private void getNyData(String cup,String max,String page) {
        Disposable d=Network.getVideoApi().actress_index(cup,max,page)
                .compose(this.<CommonlyBean<AVBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<AVBean>>() {
                    @Override
                    public void accept(CommonlyBean<AVBean> commonlyBean) throws Exception {
                        if (commonlyBean.getCode()==200){
                            List<AVBean> ny_data = commonlyBean.getData();
                            all_data=ny_data;
                            initAdapter(all_data);
                            initData(all_data);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept:","");
                    }
                });
    }

//    private void getData(String order, String cup) {
//        ProgressDialogUtil.showDialog(mContext, "加载中...", true);
//        OkGo.<String>post(ACTION_URL_GET_ZLTJ_NYSX).tag(this)
//                .params("order", order)
//                .params("cup", cup)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            ActorListBean bean = GsonUtil.parseJsonWithGson(response.body(), ActorListBean.class);
//                            if (bean != null) {
//                                ActorListBean.Data dataaa = bean.getData();
//                                if (dataaa != null) {
//                                    List<ActorListBean.Data.AvData> data = dataaa.getData();
//                                    if (!listIsNull(data)) {
//                                        all_data = data;
//                                        initAdapter(data);
//                                        initData(data);
//                                    }
//                                }
//                            }
//                        }
//                        ProgressDialogUtil.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        ProgressDialogUtil.dismiss();
//                        super.onError(response);
//                    }
//                });
//    }

    private HashMap<String, List<AVBean>> mpdata;
    private HashMap<String, String> mpCup;

    private void initData(List<AVBean> data) {
        mpdata = new HashMap<>();
        mpCup = new HashMap<>();
        if (!listIsNull(data)) {
            for (int i = 0; i < data.size(); i++) {
                AVBean avData = data.get(i);
                String cup = avData.getCup();
                if (!mpCup.containsKey(cup)) {
                    mpCup.put(cup, "");
                }
            }

            for (String newcup : mpCup.keySet()) {
                List<AVBean> newdata = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    AVBean avData = data.get(i);
                    String cup = avData.getCup();
                    if (cup.equals(newcup)) {
                        newdata.add(avData);
                    }
                }
                mpdata.put(newcup, newdata);
            }
        }
    }

    private void initAdapter(List<AVBean> data) {
        if (adapter == null) {
            adapter = new ActorAdapter(mContext, data);
            gv_actors.setAdapter(adapter);
        } else {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_renq:
//                getData("renqi", cup);
                getNyData("","1","1");
                break;
            case R.id.rb_nums:
//                getData("pianliang", cup);
                getNyData("","2","1");
                break;
            case R.id.rb_all:
                cup = "";
                data = all_data;
                initAdapter(data);
                break;
            case R.id.rb_a:
                cup = "A";
                refreshData("A");
                break;
            case R.id.rb_b:
                cup = "B";
                refreshData("B");
                break;
            case R.id.rb_c:
                cup = "C";
                refreshData("C");
                break;
            case R.id.rb_d:
                cup = "D";
                refreshData("D");
                break;
            case R.id.rb_e:
                cup = "E";
                refreshData("E");
                break;
            case R.id.rb_f:
                cup = "F";
                refreshData("F");
                break;
            case R.id.rb_g:
                cup = "G";
                refreshData("G");
                break;
            case R.id.rb_h:
                cup = "H";
                refreshData("H");
                break;
            case R.id.rb_i:
                cup = "I";
                refreshData("I");
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void refreshData(String cup) {
        if (mpdata != null && mpdata.containsKey(cup)) {
            data = mpdata.get(cup);
        } else {
            data = new ArrayList<>();
        }
        initAdapter(data);
    }

}
