package com.zcf.bananavideohannan.fragment.feekbackfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.FeedbackAdapter;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.FeedbackResultBean;
import com.zcf.bananavideohannan.util.GsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.zcf.bananavideohannan.base.Consts.RESULT_CODE_SUCCESS;

@ContentView(R.layout.fragment_fankui)
public class MyFeedBackFragment extends MyBaseFragment {
    private FeedbackAdapter adapter;
    private List<FeedbackResultBean.DataBean> data;

    @ViewInject(R.id.lv_feedbacks)
    private ListView lv_feedbacks;

    @ViewInject(R.id.tv_shaod)
    private TextView tv_shaod;

    @ViewInject(R.id.iv_none)
    private ImageView iv_none;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getData();
    }

    private void getData() {
        OkGo.<String>post(Consts.ACTION_URL_GET_FEEDBACK_LIST).tag(this)
                .headers("token",DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        tv_shaod.setVisibility(View.GONE);
                        if (200 == response.code()) {
                            FeedbackResultBean jsonobj = GsonUtil.parseJsonWithGson(response.body(), FeedbackResultBean.class);
                            if (jsonobj != null && RESULT_CODE_SUCCESS == jsonobj.getCode()) {
                                data = jsonobj.getData();
                                if (!listIsNull(data)) {
                                    iv_none.setVisibility(View.GONE);
                                    tv_shaod.setVisibility(View.GONE);
                                    lv_feedbacks.setVisibility(View.VISIBLE);
                                    initAdapter();
                                } else {
                                    iv_none.setVisibility(View.VISIBLE);
                                    tv_shaod.setVisibility(View.GONE);
                                    lv_feedbacks.setVisibility(View.GONE);
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        tv_shaod.setVisibility(View.GONE);
                        super.onError(response);
                    }
                });
    }

    private void initAdapter() {
        Collections.sort(data, new Comparator<FeedbackResultBean.DataBean>() {
            @Override
            public int compare(FeedbackResultBean.DataBean o1, FeedbackResultBean.DataBean o2) {
                return o2.getId()-o1.getId();
            }
        });
        adapter = new FeedbackAdapter(getActivity(), data);
        lv_feedbacks.setAdapter(adapter);
    }
}
