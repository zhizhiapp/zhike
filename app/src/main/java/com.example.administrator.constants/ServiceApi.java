package com.example.administrator.constants;


import com.example.administrator.entity.AddIntegralEntity;
import com.example.administrator.entity.AnswerSubmitEntity;
import com.example.administrator.entity.ChapterEntity;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.CouponEntity;
import com.example.administrator.entity.ExercisesTestItemEntity;
import com.example.administrator.entity.GetSignConfigEntity;
import com.example.administrator.entity.GetSignEntity;
import com.example.administrator.entity.HomeExercisesEntity;
import com.example.administrator.entity.HomeIntelligenceEntity;
import com.example.administrator.entity.HomeKnowledgeEntity;
import com.example.administrator.entity.HomeMicroClassEntity;
import com.example.administrator.entity.HomeMyhightscoreEntity;
import com.example.administrator.entity.HomeZhenTiEntity;
import com.example.administrator.entity.IntelligenceInfoEntity;
import com.example.administrator.entity.IntelligenceMoreEntity;
import com.example.administrator.entity.KnowledgeListEntity;
import com.example.administrator.entity.LearningSituationEntity;
import com.example.administrator.entity.LoginEntity;
import com.example.administrator.entity.MyCollectionExerciseEntity;
import com.example.administrator.entity.MyCollectionIntelligenceEntity;
import com.example.administrator.entity.MyCollectionKnowledgeEntity;
import com.example.administrator.entity.MyCollectionMicroClassEntity;
import com.example.administrator.entity.MyCollectionOpenClassEntity;
import com.example.administrator.entity.MyCollectionZhenTiEntity;
import com.example.administrator.entity.MyOpenVdEntity;
import com.example.administrator.entity.MyWrongTitleRecordsEntity;
import com.example.administrator.entity.MyhightscoreEntity;
import com.example.administrator.entity.RegisterEntity;
import com.example.administrator.entity.SmsEntity;
import com.example.administrator.entity.SoluListEntity;
import com.example.administrator.entity.SoluVideoEntity;
import com.example.administrator.entity.SolutionEntity;
import com.example.administrator.entity.StudyAnalysisEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.entity.UpdateInfoEntity;
import com.example.administrator.entity.UploadImgEntity;
import com.example.administrator.entity.VideoCatalogEntity;
import com.example.administrator.entity.VideoEntity;
import com.example.administrator.entity.VideoInfoEntity;
import com.example.administrator.entity.VideoVidPractEntity;
import com.example.administrator.entity.ZhenTiListEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ServiceApi {

//    @Query(GET请求):
//    用于在url后拼接上参数，例如：
//    @GET("book/search")
//    Call<Book> getSearchBook(@Query("q") String name);//name由调用者传入
//    相当于：
//    @GET("book/search?q=name")
//    Call<Book> getSearchBook();

//    @GET("book/search")
//    Call<Book> getSearchBook(@Query("q") String name,
//                             @Query("tag") String tag,
//                             @Query("start") int start,
//                             @Query("count") int count);


//    @QueryMap(GET请求):
//    如果入参比较多，就可以把它们都放在Map中，例如：
//    @GET("book/search")
//    Call<Book> getSearchBook(@QueryMap Map<String, String> options);
//    @Path(GET请求):
//    用于替换url中某个字段，例如：
//    @GET("group/{id}/users")
//    Call<Book> groupList(@Path("id") int groupId);


//    @GET("app/news/getlist")
//    Observable<JournalismEntity> login(@QueryMap Map<String, Object> options);


//    @GET("static/images/news/{url}")
//    Observable<JournalismEntity> img(@Path("url") int imgUrl);


//    @Headers({
//            "version:1.0.0"
//            "Cache-Control: max-age=64000"
//            })




    //验证码
    @POST("/app/system/sendCode")
    Call <SmsEntity>getYzm(@QueryMap Map<String, Object> options);

    //注册
    @POST("/app/user/")
    Call<RegisterEntity> postRegister(@QueryMap Map<String, Object> options);

    //登陆
    @POST("/app/login")
    Call<LoginEntity> postLogin(@QueryMap Map<String, Object> options);

    //找回密码
    @POST("/app/user/getpwd")
    Call<ResponseBody> getpwd(@QueryMap Map<String, Object> options);


    //首页轮播图
    @GET("/app/banner")
    Call<String> getCarousel(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //科目信息
    @GET("/app/project/getSubList")
    Call<SubjectEntity> getSubject(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);



    //首页习题
    @GET("/app/practices")
    Call<HomeExercisesEntity> getExercises(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //首页用户排名
    @GET("/app/user/myhightscore")
    Call<HomeMyhightscoreEntity> getHomeMyhightscore(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //用户排名
    @GET("/app/user/myhightscore")
    Call<MyhightscoreEntity> getMyhightscore(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);




    //首页新闻列表--智能资讯
    @GET("/app/news")
    Call<HomeIntelligenceEntity> getJournalism(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //新闻列表
    @GET("/app/news/getlist")
    Call<IntelligenceMoreEntity> getIntelligenceList(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //新闻详情
    @GET("/app/news/getInfo")
    Call<IntelligenceInfoEntity> getIntelligenceInfo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);



    //首页微课--视频
    @GET("/app/video")
    Call<HomeMicroClassEntity> getVideo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //获取首页知识库列表
    @GET("/app/knowledge")
    Call<HomeKnowledgeEntity> getKnowledge(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //获取首页真题列表
    @GET("/app/realtopic")
    Call<HomeZhenTiEntity> getZhenTi(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //获取联考数据
    @GET("/app/exam")
    Call<ResponseBody> getEntranceExam(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    @GET
    Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, Object> headerMap);

    //习题列表
    @GET("app/practices/getlist")
    Call<ExercisesTestItemEntity> getExercisesList(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //习题之章的接口
    @GET("app/practices/getPractChapt")
    Call<ExercisesTestItemEntity> getPractChapt(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //习题之章的接口
    @GET("app/practices/getPractSection")
    Call<ExercisesTestItemEntity> getPractSection(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);



    //微课章
    @GET("/app/video/getchapter")
    Call<ChapterEntity> getchapter(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //微课视频
    @GET("/app/video/getlist")
    Call<VideoEntity> getlist(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //观看视频权限
    @GET("/app/video/checkVideo")
    Call<ResponseBody> getcheckVideo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //视频详情—简介接口
    @GET("/app/video/getinfo")
    Call<VideoInfoEntity> getinfo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //视频详情--目录
    @GET("/app/video/getcatalog")
    Call<VideoCatalogEntity> getcatalog(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //视频详情--目录
    @GET("/app/video/getVidPract")
    Call<VideoVidPractEntity> getVidPract(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--修改个人信息
    @POST("/app/user/updateInfo")
    Call<UpdateInfoEntity> updateInfo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--获取个人信息
    @PUT("/app/user/getInfo")
    Call<ResponseBody> getInfo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--修改头像
    @Multipart
    @POST("/app/user/uploadImg")
    Call<UploadImgEntity> uploadImg (@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap, @Part MultipartBody.Part file);


    //我的--错题
    @GET("/app/user/myErrQuest")
    Call<MyWrongTitleRecordsEntity> myErrQuest(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //收藏
    @POST("/app/system/collection")
    Call<CollectionEntity> Collection(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //我的学习情况
    @GET("/app/user/myhightscore")
    Call<LearningSituationEntity> getLearningSituation(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //智能分析  信息对比
    @GET("/app/solution/studyAnalysis")
    Call<StudyAnalysisEntity> studyAnalysis(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //智慧方案  信息对比
    @GET("/app/solution/getSolution")
    Call<SolutionEntity> getSolution(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //智慧方案  推荐知识点
    @GET("/app/solution/getSoluList")
    Call<SoluListEntity> getSoluList(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //智慧方案  推荐视频
    @GET("/app/solution/getSoluVideo")
    Call<SoluVideoEntity> getSoluVideo(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //真题列表
    @GET("/app/realtopic/getlist")
    Call<ZhenTiListEntity> getZhenTiList(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //知识库列表
    @GET("/app/knowledge/getlist")
    Call<KnowledgeListEntity> getknowledgeList(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);




    //我的--学习记录--足迹
    @GET("/app/knowledge/getlist")
    Call<KnowledgeListEntity> getaaaaaaaaaaaa(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--学习记录--阅卷
    @GET("/app/knowledge/getlist")
    Call<KnowledgeListEntity> getbbbbbbbbbbbbbb(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //我的--收藏--微课
    @GET("/app/user/myCollection")
    Call<MyCollectionMicroClassEntity> myCollectionMicroClass(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--收藏--习题
    @GET("/app/user/myCollection")
    Call<MyCollectionExerciseEntity> myCollectionExercise(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--收藏--真题
    @GET("/app/user/myCollection")
    Call<MyCollectionZhenTiEntity> myCollectionZhenTi(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--收藏--知识库
    @GET("/app/user/myCollection")
    Call<MyCollectionKnowledgeEntity> myCollectionKnowledge(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--收藏--公开课
    @GET("/app/user/myCollection")
    Call<MyCollectionOpenClassEntity> myCollectionOpenClass(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--收藏--资讯
    @GET("/app/user/myCollection")
    Call<MyCollectionIntelligenceEntity> myCollectionIntelligence(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //18302088970     123456
    //我的--公开课
    @GET("/app/video/getOpenVd")
    Call<MyOpenVdEntity> getOpenVd(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--签到--可兑换优惠券
    @GET("/app/coupon/getCoupon")
    Call<CouponEntity> getCoupon(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--签到--用户签到
    @POST("/app/integral/addSign")
    Call<ResponseBody> addSign(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--签到--赚积分
    @POST("/app/integral/addIntegral")
    Call<AddIntegralEntity> addIntegral(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--签到--用户的签到和积分消息
    @GET("/app/integral/getSign")
    Call<GetSignEntity> getSign(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //我的--签到--获取积分配置信息
    @GET("/app/integral/getSignConfig")
    Call<GetSignConfigEntity> getSignConfig(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    //添加购物车
    @POST("/app/order/addCart")
    Call<ResponseBody> addCart(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);

    //删除购物车
    @POST("/app/order/delCart")
    Call<ResponseBody> delCart(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);




    /**
     * 管理类联考提交
     *
     * @param options
     * @param headerMap
     * @return
     */
    @POST("app/exam/assignment")
    Call<AnswerSubmitEntity> submitExam(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);


    /**
     * 管理类联考提交分析问题
     *
     * @param options
     * @param headerMap
     * @return
     */
    @GET("app/exam/analysis")
    Call<ResponseBody> getAnswerAnalysis(@QueryMap Map<String, Object> options, @HeaderMap Map<String, Object> headerMap);



}