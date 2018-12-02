package com.empathy.empathy_android.repository

import android.util.Log
import com.empathy.empathy_android.di.qualifier.App
import com.empathy.empathy_android.extensions.toErrorSwallowingObservable
import com.empathy.empathy_android.http.EmpathyApi
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


internal class EmpathyRepository @Inject constructor(
             private val appChannel: AppChannelApi,
             private val empathyApi: EmpathyApi,
        @App private val disposables: CompositeDisposable
): EmpathyRepositoryApi {

    init {
        disposables.addAll(
                appChannel.ofData()
                        .ofType(AppData.RequestTo.Remote::class.java)
                        .observeOn(Schedulers.io())
                        .flatMap {
                            when(it) {
                                is AppData.RequestTo.Remote.FetchFeedsByLocationFilter -> {
                                    empathyApi.fetchFeedsByLocationFilter(it.locationEnum, it.userId)
                                            .map {
                                                AppData.RespondTo.Remote.FeedsByLocationFilterFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.FetchDetailFeed -> {
                                    empathyApi.fetchFeedDetail(it.feedId)
                                            .map {
                                                AppData.RespondTo.Remote.FeedDetailFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.FetchMyFeeds -> {
                                    empathyApi.fetchMyFeeds(it.userId)
                                            .map {
                                                AppData.RespondTo.Remote.MyFeedsFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.CreateUser -> {
                                    empathyApi.createUser(it.user)
                                            .map {
                                                AppData.RespondTo.Remote.UserCreated(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.CreateFeed -> {
                                    empathyApi.createFeed(
                                            it.userId,
                                            it.parameterTitle,
                                            it.parameterDesc,
                                            it.address,
                                            it.userLocationEnum,
                                            it.multipartBody
                                    ).map {
                                        AppData.RespondTo.Remote.FeedCreated(it)
                                    }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.DeleteFeed -> {
                                    val position = it.adapterPosition

                                    empathyApi.deleteFeed(it.targetId)
                                            .map {
                                                AppData.RespondTo.Remote.FeedDeleted(it, position)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.FetchTourInfos -> {
                                    empathyApi.fetchTourInfos(
                                            it.contentType,
                                            it.latitude,
                                            it.longtitude,
                                            it.range,
                                            it.perPage)
                                            .map {
                                                AppData.RespondTo.Remote.TourInfosFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.FetchPartnerInfo -> {
                                    empathyApi.fetchPartnerInfo()
                                            .map {
                                                AppData.RespondTo.Remote.PartnerInfoFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                                is AppData.RequestTo.Remote.FetchPartnerInfoDetail -> {
                                    empathyApi.fetchPartnerInfoDetail(it.partnerId)
                                            .map {
                                                AppData.RespondTo.Remote.PartnerInfoDetailFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                            }
                        }.subscribe(appChannel::accept)
        )
    }
}