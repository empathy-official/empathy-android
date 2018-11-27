package com.empathy.empathy_android.http

import com.empathy.empathy_android.repository.model.Feed
import com.empathy.empathy_android.repository.model.LocationEnum
import io.reactivex.Single


internal interface EmpathyApi {
    fun fetchFeedsByLocationFilter(locationEnum: LocationEnum): Single<List<Feed>>
}