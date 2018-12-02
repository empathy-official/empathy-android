package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


internal interface EmpathyApi {

    @GET("/journey/main/{locationEnum}/{userId}")
    fun fetchFeedsByLocationFilter(
            @Path("locationEnum") locationEnum: LocationEnum,
            @Path("userId") userId: String

    ): Single<FeedMain>

    @GET("/journey/{targetId}")
    fun fetchFeedDetail(@Path("targetId") feedId: Int): Single<FeedDetail>

    @GET("/journey/myjourney/{ownerId}")
    fun fetchMyFeeds(@Path("ownerId") userId: String): Single<MutableList<MyFeed>>

    @POST("/user/")
    fun createUser(@Body user: User): Single<Long>

    @Multipart
    @POST("/journey/")
    fun createFeed(
            @Part("ownerId") userId: RequestBody,
            @Part("title") parameterTitle: RequestBody,
            @Part("contents") parameterDesc: RequestBody,
            @Part("location") address: RequestBody,
            @Part("locationEnum") userLocationEnum: RequestBody,
            @Part multipartBody: MultipartBody.Part

    ): Single<ResponseBody>

    @DELETE("/journey/{targetId}")
    fun deleteFeed(@Path("targetId") targetId: Int): Single<ResponseBody>

    @GET("/info/tourAPI2/{contentType}/{mapX}/{mapY}/{range}/{pageNumber}")
    fun fetchTourInfos(
        @Path("contentType") contentType: Int,
        @Path("mapX") latitude: Double,
        @Path("mapY") longtitude: Double,
        @Path("range") range: String,
        @Path("pageNumber") perPage: Int

    ): Single<MutableList<TourInfo>>

    @GET("/info/alliance")
    fun fetchPartnerInfo(): Single<Partner>

    @GET("/info/alliance/detail/{targetId}")
    fun fetchPartnerInfoDetail(@Path("targetId") partnerId: String): Single<PartnerDetail>

    @GET("/info/tourAPI/detail/{contentType}/{targetId}")
    fun fetchTourInfoDetail(@Path("contentType") contentType: String, @Path("targetId") targetId: String): Single<TourDetail>


}