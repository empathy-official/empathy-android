package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


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

}