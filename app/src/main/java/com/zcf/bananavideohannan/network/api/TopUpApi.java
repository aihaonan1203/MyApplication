// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcf.bananavideohannan.network.api;


import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.bean.Ttt;
import com.zcf.bananavideohannan.bean.ZhifuBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface TopUpApi {


    @FormUrlEncoded
    @POST("api/user/aaa")//发起支付
    Observable<ResponseBody> topup(@Header("token")String token, @Field("legalAmount") String legalAmount, @Field("type") String type, @Field("mobile") String mobile);

    @POST("api/user/bbb")//查询支付
    Observable<CommonlyBean<ZhifuBean>> getTopUp(@Header("token")String token);

    @FormUrlEncoded
    @POST("api/user/ccc")//确认
    Observable<ResponseBody> affirm(@Header("token")String token, @Field("preId") String preId, @Field("name") String name);

    @Multipart
    @POST("api/user/payurl")
    Observable<ResponseBody> postVisitsInfo(@Header("token")String token,@Part MultipartBody.Part payurl);

    @POST("api/user/moneylog")
    Observable<Ttt> moneylog(@Header("token")String token);

}
