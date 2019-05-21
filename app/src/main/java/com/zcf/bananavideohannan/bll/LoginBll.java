package com.zcf.bananavideohannan.bll;

import android.content.Context;

import com.gaotai.baselib.DcAndroidContext;

public class LoginBll {
    private Context mContext;
    private DcAndroidContext app;

    public LoginBll(Context mContext) {
        this.mContext = mContext;
        app = (DcAndroidContext) mContext.getApplicationContext();
    }


    public void getPersonInfo(String uid) {
//        OkGo.<String>post(Consts.ACTION_URL_GET_USERINFO).tag(this).isMultipart(false)
//                .params("uid", uid)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        try {
//                            if (200 == response.code()) {
//                                PersonInfoBean bean = GsonUtil.parseJsonWithGson(response.body(), PersonInfoBean.class);
//                                if (bean != null) {
//                                    if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
//                                        // 获取成功
//                                        app.setParam(USER_ISREFRESH_PERSONINFO, USER_FRESH_NO);
//                                        UserInfoDomain userData = bean.getUserData();
//                                        if (userData != null) {
//                                            ContextProperties.writeProperties(mContext, userData);
//                                        }
//                                    }
//                                }
//                            }
////                            ProgressDialogUtil.dismiss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
////                        ProgressDialogUtil.dismiss();
//                        super.onError(response);
//                    }
//                });
    }
}
