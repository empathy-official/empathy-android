package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal interface FeedViewModel {

    class ViewModel @Inject constructor(
            val channel: FeedChannelApi,
            val appChannel: AppChannelApi

    ): BaseViewModel() {

        private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

        init {
            compositeDisposable.addAll(
                    onCreate.subscribeBy(
                            onNext = ::handleOnCreate
                    )
            )
        }

        private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {

        }
    }

}
