package com.empathy.empathy_android.ui.myfeed

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.repository.model.LocalUser
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class MyFeedViewModel @Inject constructor(
                val channel: MyFeedChannelApi,
        private val appChannel: AppChannelApi

): BaseViewModel() {

    private val onCreate         = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)
    private val onActivityResult = channel.ofLifeCycle().ofType(LifecycleState.OnActivityResult::class.java)
    private val onViewAction     = channel.ofViewAction()

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var user: LocalUser = LocalUser()

    val showMyFeeds = MutableLiveData<MyFeedLooknFeel.ShowMyFeeds>()
    val showEmptyFeeds = MutableLiveData<MyFeedLooknFeel.ShowEmptyFeeds>()
    val deleteMyFeed = MutableLiveData<MyFeedLooknFeel.DeleteMyFeed>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onActivityResult.subscribeBy(onNext = ::handleOnActivityResult),

                onRemote.subscribeBy(onNext = ::handleOnRemote),

                onViewAction.subscribeBy(onNext = ::handleOnViewAction)
        )
    }

    private fun handleOnViewAction(viewAction: MyFeedViewAction) {
        when(viewAction) {
            is MyFeedViewAction.OnFeedDeleteClicked -> {
                viewAction.targetId?.let {
                    appChannel.accept(AppData.RequestTo.Remote.DeleteFeed(it, viewAction.adapterPosition))
                }
            }
        }
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        user = onCreate.intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        appChannel.accept(AppData.RequestTo.Remote.FetchMyFeeds(user.userId.toString()))
    }

    private fun handleOnActivityResult(onActivityResult: LifecycleState.OnActivityResult) {
        val resultCode  = onActivityResult.resultCode
        val requestCode = onActivityResult.requestCode
        val data        = onActivityResult.data

        if(resultCode == Activity.RESULT_OK && requestCode == MyFeedsActivity.REQUEST_CODE_ALBUM) {
            data?.data?.let {
                channel.accept(MyFeedNavigation.NavigateToFeedInput(it.toString(), user))
            }
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

            is AppData.RespondTo.Remote.FeedDeleted -> {
                val deletePosition = remote.position

                deleteMyFeed.postValue(MyFeedLooknFeel.DeleteMyFeed(deletePosition))
            }
        }
    }

}
