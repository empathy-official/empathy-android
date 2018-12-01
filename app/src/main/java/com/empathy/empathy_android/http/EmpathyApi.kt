package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun createFeed(@Part("ownerId") userId: RequestBody, @Part("title") parameterTitle: RequestBody, @Part("contents") parameterDesc: RequestBody, @Part("location") address: RequestBody, @Part("locationEnum") userLocationEnum: RequestBody, @Part multipartBody: MultipartBody.Part): Single<Any>

    @DELETE("/journey/{targetId}")
    fun deleteFeed(@Path("targetId") targetId: Int): Single<Any>

    @GET("/info/tourAPI/{contentType}/{mapX}/{mapY}/{range}/{pageNumber}")
    fun fetchTourInfos(
        @Path("contentType") contentType: Int,
        @Path("mapX") latitude: Double,
        @Path("mapY") longtitude: Double,
        @Path("range") range: String,
        @Path("pageNumber") perPage: Int

    ): Single<MutableList<TourInfo>>

//    @Multipart
//    @POST("/journey/")
//    fun createFeed(
//            @Path("ownerId") userId: String,
//            @Path("title") title: String,
//            @Path("contents") description: String,
//            @Path("location") address: String,
//            @Path("locationEnum") userLocationEnum: LocationEnum,
//            @Part multipartBody: MultipartBody.Part
//
//    ): Single<Nothing>

}