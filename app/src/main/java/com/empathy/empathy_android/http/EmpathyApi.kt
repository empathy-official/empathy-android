package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.Feed
import com.empathy.empathy_android.repository.model.FeedDetail
import com.empathy.empathy_android.repository.model.LocationEnum
import com.empathy.empathy_android.repository.model.MyFeed
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


internal interface EmpathyApi {

    @GET("/journey/location/{locationEnum}")
    fun fetchFeedsByLocationFilter(@Path("locationEnum") locationEnum: LocationEnum): Single<MutableList<Feed>>

    @GET("/journey/{targetId}")
    fun fetchFeedDetail(@Path("targetId") feedId: Int): Single<FeedDetail>

    @GET("/journey/myjourney/{ownerId}")
    fun fetchMyFeeds(@Path("ownerId") userId: Int): Single<MutableList<MyFeed>>

}