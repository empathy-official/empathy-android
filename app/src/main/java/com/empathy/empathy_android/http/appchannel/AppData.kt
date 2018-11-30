package com.empathy.empathy_android.http.appchannel

import com.empathy.empathy_android.repository.model.*


internal sealed class AppData {

    sealed class RequestTo: AppData() {
        sealed class Remote: RequestTo() {
            data class FetchFeedsByLocationFilter(val userId: String, val locationEnum: LocationEnum) : Remote()

            data class FetchDetailFeed(val feedId: Int) : Remote()

            data class FetchMyFeeds(val userId: Int) : Remote()

            data class CreateUser(val user: User) : Remote()
        }
    }

    sealed class RespondTo: AppData() {
        sealed class Remote: RespondTo() {
            data class UserCreated(val userId: Long): Remote()

            data class FeedsByLocationFilterFetched(val feedMain: FeedMain): Remote()

            data class FeedDetailFetched(val detailFeed: FeedDetail): Remote()

            data class MyFeedsFetched(val myFeeds: MutableList<MyFeed>): Remote()

        }
    }
}
