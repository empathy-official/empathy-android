package com.empathy.empathy_android.http.appchannel

import com.empathy.empathy_android.repository.model.Feed
import com.empathy.empathy_android.repository.model.FeedDetail
import com.empathy.empathy_android.repository.model.LocationEnum


internal sealed class AppData {

    sealed class RequestTo: AppData() {
        sealed class Remote: RequestTo() {
            data class FetchFeedsByLocationFilter(val locationEnum: LocationEnum) : Remote()

            data class FetchDetailFeed(val feedId: Int) : Remote()

        }
    }

    sealed class RespondTo: AppData() {
        sealed class Remote: RespondTo() {
            data class FeedsByLocationFilterFetched(val feeds: MutableList<Feed>): Remote()

            data class FeedDetailFetched(val detailFeed: FeedDetail): Remote()

        }
    }
}
