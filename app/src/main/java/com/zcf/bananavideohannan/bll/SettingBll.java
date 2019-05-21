package com.zcf.bananavideohannan.bll;

import android.content.Context;

import com.gaotai.baselib.DcAndroidContext;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.bean.VxInfoResultBean;
import com.zcf.bananavideohannan.util.GsonUtil;

public class SettingBll {
    private Context mContext;
    private DcAndroidContext app;

    public SettingBll(Context mContext) {
        this.mContext = mContext;
        app = (DcAndroidContext) mContext.getApplicationContext();
    }

    public void getVXInfo() {
        OkGo.<String>post(Consts.ACTION_URL_GET_XWINFO).tag(this).isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (200 == response.code()) {
                                VxInfoResultBean bean = GsonUtil.parseJsonWithGson(response.body(), VxInfoResultBean.class);
                                if (bean != null) {
                                    if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                        // 获取成功
                                        VxInfoResultBean.VxInfoBean data = bean.getData();
                                        if (data != null) {
                                            app.setParam(Consts.USER_VX_EWM, data.getImage());
                                            app.setParam(Consts.USER_VX_ACCOUNT, data.getWx_num());
                                            app.setParam(Consts.USER_VX_CONTENT, data.getExplain());
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
