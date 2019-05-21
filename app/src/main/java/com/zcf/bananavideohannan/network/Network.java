// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcf.bananavideohannan.network;


import com.zcf.bananavideohannan.network.api.TopUpApi;
import com.zcf.bananavideohannan.network.api.VideoApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static VideoApi gankApi;
    private static TopUpApi topUpApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static VideoApi getVideoApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://203.78.142.214/index.php/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(VideoApi.class);
        }
        return gankApi;
    }

    public static TopUpApi getTopUpApi() {
        if (topUpApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://203.78.142.214/index.php/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            topUpApi = retrofit.create(TopUpApi.class);
        }
        return topUpApi;
    }

}
