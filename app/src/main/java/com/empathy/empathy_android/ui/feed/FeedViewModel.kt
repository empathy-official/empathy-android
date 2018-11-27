package com.empathy.empathy_android.ui.feed

import android.util.Log
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.login.LocationFilterApi
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal interface FeedViewModel {

    class ViewModel @Inject constructor(
                               val channel: FeedChannelApi,
                       private val appChannel: AppChannelApi,
            @LocFilter private var locationFilter: LocationFilterApi

    ): BaseViewModel() {

        private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

        private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

        init {
            compositeDisposable.addAll(
                    onCreate.subscribeBy(
                            onNext = ::handleOnCreate
                    ),

                    onRemote.subscribeBy(
                            onNext = ::handleOnRemote
                    )
            )
        }

        private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
            val locationAddr = locationFilter.getLocationEnum()?.location
            val locationCode = locationFilter.getLocationEnum()?.code

            Log.d("123sdf", locationFilter.getLocationEnum()?.location + " , " + locationFilter.getLocationEnum()?.code)

            if(locationAddr == null || locationAddr == "") {

            } else {
                //TODO: activity에 bool값 설정
                appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter(locationFilter.getLocationEnum()!!))
            }
        }

        private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
            when(remote) {
                is AppData.RespondTo.Remote.FeedsByLocationFilterFetched -> {

                }
            }
        }
    }

}
