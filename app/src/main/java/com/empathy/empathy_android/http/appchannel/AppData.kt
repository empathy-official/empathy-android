package com.empathy.empathy_android.http.appchannel

import com.empathy.empathy_android.repository.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


internal sealed class AppData {

    sealed class RequestTo: AppData() {
        sealed class Remote: RequestTo() {
            object FetchPartnerInfo : Remote()

            data class FetchFeedsByLocationFilter(val userId: String, val locationEnum: LocationEnum) : Remote()

            data class FetchDetailFeed(val feedId: Int) : Remote()

            data class FetchMyFeeds(val userId: String) : Remote()

            data class CreateUser(val user: User) : Remote()

            data class CreateFeed(val userId: RequestBody, val parameterTitle: RequestBody, val parameterDesc: RequestBody, val address: RequestBody, val userLocationEnum: RequestBody, val multipartBody: MultipartBody.Part) : Remote()

            data class DeleteFeed(val targetId: Int, val adapterPosition: Int) : Remote()

            data class FetchTourInfos(
                    val contentType: Int,
                    val latitude: Double,
                    val longtitude: Double,
                    val range: String,
                    val perPage: Int

            ) : Remote()


//            data class CreateFeed(
//                    val userId: Long,
//                    val title: String,
//                    val description: String,
//                    val address: String,
//                    val userLocationEnum: LocationEnum,
//                    val multipartBody: MultipartBody.Part
//
//            ) : Remote()
        }
    }

    sealed class RespondTo: AppData() {
        sealed class Remote: RespondTo() {
            data class UserCreated(val userId: Long): Remote()

            data class FeedsByLocationFilterFetched(val feedMain: FeedMain): Remote()

            data class FeedDetailFetched(val detailFeed: FeedDetail): Remote()

            data class MyFeedsFetched(val myFeeds: MutableList<MyFeed>): Remote()

            data class FeedCreated(val it: Any): Remote()

            data class FeedDeleted(val it: Any, val position: Int): Remote()

            data class TourInfosFetched(val tourInfos: MutableList<TourInfo>): Remote()

            data class PartnerInfoFetched(val partner: Partner): Remote()
        }
    }
}
