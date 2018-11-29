package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.Feed
import com.empathy.empathy_android.repository.model.FeedDetail
import com.empathy.empathy_android.repository.model.LocationEnum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


internal interface EmpathyApi {

    @GET("/journey/location/{locationEnum}")
    fun fetchFeedsByLocationFilter(@Path("locationEnum") locationEnum: LocationEnum): Single<MutableList<Feed>>

    fun fetchFeedDetail(feedId: Int): Single<FeedDetail>

}