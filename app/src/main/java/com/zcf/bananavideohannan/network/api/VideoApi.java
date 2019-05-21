// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zcf.bananavideohannan.network.api;

import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.bean.CommentlistBean;
import com.zcf.bananavideohannan.bean.GGBean;
import com.zcf.bananavideohannan.bean.GuangGaoBean;
import com.zcf.bananavideohannan.bean.IssueBean;
import com.zcf.bananavideohannan.bean.LabelBean;
import com.zcf.bananavideohannan.bean.LikeBean;
import com.zcf.bananavideohannan.bean.LishiBean;
import com.zcf.bananavideohannan.bean.MyResponseBody;
import com.zcf.bananavideohannan.bean.TixianBean;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface VideoApi {
    @POST("api/videolist/news_video")//最新
    Observable<Object> getCritic(@Header ("token")String token, @Query("page")int page);

    @FormUrlEncoded
    @POST("api/user/discover")//视频
    Observable<CommonlyBean<VideoBean>> getFind(@Header ("token")String token,@Field("page") int page);

    @POST("api/videolist/play_channel")//映前广告
    Observable<ResponseBody> getplay_channel();

    @POST("api/user/commentlist")//获取评论
    Observable<CommonlyBean<CommentlistBean>> getComment(@Header("token")String token, @Query("vid")int vid, @Query("new")String type);

    @POST("api/videolist/label_index")//标签列表
    Flowable<CommonlyBean<LabelBean>> getLabel_index();

    @POST("api/user/views")//视频点击量  观看次数-1  历史记录  点击视频列表时调用
    Observable<ResponseBody> getVideoNum(@Header ("token")String token,@Query("id")int id);

    @POST("api/user/evaluate")//踩/赞
    Observable<ResponseBody> setEvaluate(@Header ("token")String token,@Query("vid")int id,@Query("type")int type);

    @FormUrlEncoded
    @POST("api/user/like")//添加到我的喜欢
    Observable<ResponseBody> setLike(@Header ("token")String token,@Field("id") String id);

    @POST("api/user/dellike")//取消喜欢
    Observable<ResponseBody> cancelLike(@Header ("token")String token,@Query("likeid")String id);

    @FormUrlEncoded
    @POST("api/user/comment")//添加视频评论
    Observable<ResponseBody> setComment(@Header ("token")String token,@Query("vid")String vid,@Field("comment")String comment);

    @FormUrlEncoded
    @POST("api/user/footprint")//历史足迹
    Observable<CommonlyBean<LishiBean>> getFootprint(@Header ("token")String token, @Field("time")String vid);

    @POST("api/user/mylike")//得到我的喜欢
    Observable<CommonlyBean<LikeBean>> getMyLike(@Header ("token")String token);

    @FormUrlEncoded
    @POST("api/user/delfp")//删除历史足迹
    Observable<ResponseBody> delfp(@Header ("token")String token,@Field("fpid") String id);

    @FormUrlEncoded
    @POST("api/user/dellike")//取消喜欢
    Observable<ResponseBody> dellike(@Header ("token")String token,@Field("likeid") String id);

    @FormUrlEncoded
    @POST("api/user/vip")//激活会员
    Observable<ResponseBody> vip(@Header ("token")String token,@Field("vipkey") String vipkey);

    @FormUrlEncoded
    @POST("api/videolist/special")//标签
    Observable<ZltjTjResultBean> special_index(@Field("flag") String flag, @Field("page") String page);

    @FormUrlEncoded
    @POST("api/videolist/channel")//广告
    Observable<CommonlyBean<GuangGaoBean>> getChannel(@Field("type") String type);

    @POST("api/user/exchange")//交流群
    Observable<ResponseBody> exchange();

    @POST("api/user/inform")//通知公告
    Observable<CommonlyBean<GGBean>> inform();

    @POST("api/videolist/play_channel")
    @Streaming//使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    Flowable<ResponseBody> download();

    @POST("api/user/problem")//常见问题
    Observable<CommonlyBean<IssueBean>> problem(@Header ("token")String token);

    @POST("/api/index/launch")//启动图
    Observable<MyResponseBody> launch();

    @FormUrlEncoded
    @POST("/api/index/password")//集团密码
    Observable<MyResponseBody> password(@Field("password") String password);

    @POST("api/user/xxx")//提现详情
    Observable<CommonlyBean<TixianBean>> withdrawlist(@Header ("token")String token);

    @FormUrlEncoded
    @POST("api/videolist/label_video")//标签查询视频(频道)
    Observable<CommonlyBean<VideoBean>> label_video(@Field("label") String label);

    @FormUrlEncoded
    @POST("api/user/ttt")//提现申请
    Observable<ResponseBody> withdraw(@Header ("token")String token,@Field("type") int type,@Field("legalAmount") String legalAmount,@Field("mobile") String mobile,@Field("accountName") String accountName,@Field("payurl") String payurl,@Field("account") String account,@Field("bankName") String bankName);

    @FormUrlEncoded
    @POST("api/videolist/keyword_video")//视频查询(首页)
    Observable<CommonlyBean<VideoBean>> keyword_video(@Field("keyword") String keyword,@Field("page") String page);

    @FormUrlEncoded
    @POST("api/videolist/actress_index")//频道人气女优 (一个参数 max=1)   演员列表(三个参数)
    Observable<CommonlyBean<AVBean>> actress_index(@Field("cup") String cup, @Field("max") String max, @Field("page") String page);


}
