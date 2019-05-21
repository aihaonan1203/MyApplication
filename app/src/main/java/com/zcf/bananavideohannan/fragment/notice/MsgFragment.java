package com.zcf.bananavideohannan.fragment.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 专栏推荐
 */
@ContentView(R.layout.fragment_notice_msg)
public class MsgFragment extends MyBaseFragment {

    @ViewInject(R.id.lv_msg)
    private ListView lv_msg;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getTjData();

    }

//    private void initZtAdapter() {
//        ztAdapter = new ZhuanlanGridAdapter(getActivity(), zt_data);
//        gv_rmzt.setAdapter(ztAdapter);
//
//    }
//
//
//    private void getTjData() {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkGo.<String>post(ACTION_URL_GET_ZLTJ_TJ).tag(this)
//                        .params("labels", "")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                if (200 == response.code()) {
//                                    if (response.body() != null) {
//                                        ZltjTjResultBean bean = GsonUtil.parseJsonWithGson(response.body(), ZltjTjResultBean.class);
//                                        if (bean != null) {
////                                            tj_data = bean.getData();
//                                        }
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onError(Response<String> response) {
//                                super.onError(response);
//                            }
//                        });
//            }
//        });
//        thread.start();
//        thread = null;
//    }

}
