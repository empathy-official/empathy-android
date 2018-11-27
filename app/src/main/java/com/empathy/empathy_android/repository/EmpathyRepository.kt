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
                                    empathyApi.fetchFeedsByLocationFilter(it.locationEnum)
                                            .map {
                                                AppData.RespondTo.Remote.FeedsByLocationFilterFetched(it)
                                            }.toErrorSwallowingObservable { throwable -> Log.d(EmpathyRepository::class.simpleName, throwable.toString()) }
                                }
                            }
                        }.subscribe(appChannel::accept)
        )
    }
}