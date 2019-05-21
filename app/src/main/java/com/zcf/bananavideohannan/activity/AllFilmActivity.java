package com.zcf.bananavideohannan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.AllFilmAdapter;
import com.zcf.bananavideohannan.adapter.RevyAllFilmTypeAdapter;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.BestNewBean;
import com.zcf.bananavideohannan.bean.DefTypeBean;
import com.zcf.bananavideohannan.dbmodel.FilmDBModel;
import com.zcf.bananavideohannan.util.GsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContentView(R.layout.activity_all_film)
public class AllFilmActivity extends MyBaseActivity {
    private List<FilmDBModel> data;
    private AllFilmAdapter filmAdapter;

    @ViewInject(R.id.gv_all_films)
    private GridView gv_all_films;

    @ViewInject(R.id.recy_type)
    private RecyclerView recy_type;

    @ViewInject(R.id.edt_search_input)
    private EditText edt_search_input;

    private RevyAllFilmTypeAdapter typeAdapter;

    private List<DefTypeBean.DataBean> typeData=new ArrayList<>();
    private String current_sortType = "0";// 排序方式
    private String current_sort = "0";// 类型
    private int current_page = 1;// 页数
    private DcAndroidContext app;
    public static final String EXTRA_LIST_TYPE_DATA = "extra_list_type_data";
    public static final String EXTRA_TYPE_ID = "extra_type_id";
    public static final String EXTRA_TYPE_SORT = "extra_type_sort";
    private RecyclerView allfilmrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (DcAndroidContext) mContext.getApplicationContext();

        if (app != null && app.getParam(EXTRA_LIST_TYPE_DATA) != null) {
            typeData.add(new DefTypeBean.DataBean(0,"全部"));
            typeData.addAll((List<DefTypeBean.DataBean>) app.getParam(EXTRA_LIST_TYPE_DATA));
            if (!listIsNull(typeData)) {
                initTypeDataAdaptger();
            }
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_TYPE_SORT)) {
                current_sort = bundle.getString(EXTRA_TYPE_SORT);
            }
        }

        loadTypeData(current_sortType, current_sort, current_page);
        setListeners();
        gv_all_films.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            LOG.d("AAAAAAAAAAAAAAA", "1111111111111111111111111111");
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void setListeners() {
        edt_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String inputtest = edt_search_input.getText().toString().trim();
                    if (TextUtils.isEmpty(inputtest)) {
                        ToastUtil.toastShort(mContext, "请输入搜索内容");
                    } else {
                        mpSelected = new HashMap<>();
                        typeAdapter.setMp_selected(mpSelected);
                        typeAdapter.notifyDataSetChanged();
                        loadTypeData(current_sortType, inputtest, 1);
                    }
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取数据
     *
     * @param order 排序方式
//     * @param sort  分类名称
     * @param page  页数（可选）
     */
    private void loadTypeData(String order, String category, int page) {
        if (category.equals("全部")){
            category="";
        }
        ProgressDialogUtil.showDialog(mContext, "", false);
        OkGo.<String>post(ACTION_URL_GET_VIDEO_BY_TYPE).tag(this).isMultipart(false)
                .params("order", order)
                .params("category", category)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            BestNewBean bean = GsonUtil.parseJsonWithGson(response.body(), BestNewBean.class);
                            if (bean != null) {
                                List<BestNewBean.DataBean> data = bean.getData();
                                initAdapter(data);
                            }
                        }
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressDialogUtil.dismiss();
                        super.onError(response);
                    }
                });
    }

    private void initAdapter(List<BestNewBean.DataBean> data) {
        if (filmAdapter == null) {
            filmAdapter = new AllFilmAdapter(mContext, data);
            gv_all_films.setAdapter(filmAdapter);
        } else {
            filmAdapter.setData(data);
            filmAdapter.notifyDataSetChanged();
        }

    }

    HashMap<Integer, String> mpSelected;
    private DefTypeBean.DataBean typeall;

    // 顶部分类adapter
    private void initTypeDataAdaptger() {
        for (int i = 0; i < typeData.size(); i++) {
            if ("全部".equals(typeData.get(i).getName())) {
                typeall = typeData.get(i);
                typeData.remove(i);
            }
        }
        if (typeall != null) {
            typeData.add(0, typeall);
        }
        mpSelected = new HashMap<>();
        int select_id = -1;
        String select_sort = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_TYPE_ID)) {
                select_id = bundle.getInt(EXTRA_TYPE_ID);
            }
            if (bundle.containsKey(EXTRA_TYPE_SORT)) {
                select_sort = bundle.getString(EXTRA_TYPE_SORT);
            }
        }

        typeAdapter = new RevyAllFilmTypeAdapter(mContext, typeData);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_type.setLayoutManager(manager);

        if (select_id > -2 && !TextUtils.isEmpty(select_sort)) {
            mpSelected.put(select_id, select_sort);
        }
        typeAdapter.setMp_selected(mpSelected);
        recy_type.setAdapter(typeAdapter);

        typeAdapter.setClickChooseType(new RevyAllFilmTypeAdapter.onClickChooseType() {
            @Override
            public void clickChooseType(int typeid, String sort) {
                if (mpSelected != null) {
                    mpSelected.clear();
                    mpSelected = new HashMap<>();
                    mpSelected.put(typeid, sort);
                    current_sort = sort;
                    typeAdapter.setMp_selected(mpSelected);
                    typeAdapter.notifyDataSetChanged();
                }
                loadTypeData(current_sortType, current_sort, current_page);
            }
        });


    }

    @Event(R.id.tv_cancle)
    private void cancle(View view) {
        finish();
    }

    @Event(R.id.iv_delete_input)
    private void clearInput(View view) {
        edt_search_input.setText("");
    }

    @Event(R.id.rb_sort_all)
    private void searchAll(View view) {
        current_sortType = "0";
        loadTypeData(current_sortType, current_sort, 1);
    }

    @Event(R.id.rb_sort_more)
    private void searchMore(View view) {
        current_sortType = "1";
        loadTypeData(current_sortType, current_sort, 1);
    }

    @Event(R.id.rb_sort_update)
    private void searchUpadte(View view) {
        current_sortType = "2";
        loadTypeData(current_sortType, current_sort, 1);
    }

    @Event(R.id.rb_sort_like)
    private void searchLike(View view) {
        current_sortType = "3";
        loadTypeData(current_sortType, current_sort, 1);
    }

    @Event(R.id.rlyt_seasrch_parent)
    private void searchInput(View view) {
        startActivity(new Intent(mContext, SearchActivity.class));

//        String inputtest = edt_search_input.getText().toString().trim();
//        if (TextUtils.isEmpty(inputtest)) {
//            ToastUtil.toastShort(mContext, "请输入搜索内容");
//            return;
//        }
//        loadTypeData(current_sortType, inputtest, 1);
    }

}
