package com.empathy.empathy_android.ui.mypage

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class MyFeedViewModel @Inject constructor(
                val channel: MyFeedChannelApi,
        private val appChannel: AppChannelApi

): BaseViewModel() {

    private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    val showMyFeeds = MutableLiveData<MyFeedLooknFeel.ShowMyFeeds>()
    val showEmptyFeeds = MutableLiveData<MyFeedLooknFeel.ShowEmptyFeeds>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        val userId = onCreate.intent.getIntExtra(MyFeedsActivity.EXTRA_KEY_USER_ID, -1)

        if(userId == -1) {
            showEmptyFeeds.postValue(MyFeedLooknFeel.ShowEmptyFeeds)
        } else {
            appChannel.accept(AppData.RequestTo.Remote.FetchMyFeeds(userId))
        }
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.MyFeedsFetched -> {
                val myFeeds = remote.myFeeds

                if(myFeeds.size == 0) {
                    showEmptyFeeds.postValue(MyFeedLooknFeel.ShowEmptyFeeds)
                } else {
                    showMyFeeds.postValue(MyFeedLooknFeel.ShowMyFeeds(remote.myFeeds))
                }
            }
        }
    }

}
