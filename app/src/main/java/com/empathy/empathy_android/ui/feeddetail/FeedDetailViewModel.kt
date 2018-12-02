package com.empathy.empathy_android.ui.feeddetail

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class FeedDetailViewModel @Inject constructor(
                val channel: FeedDetailChannelApi,
        private val appChannel: AppChannelApi

): BaseViewModel() {

    private val onCreate = channel.ofLifeCycle().ofType(ActivityLifecycleState.OnCreate::class.java)

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    val showFeedDetail   = MutableLiveData<FeedDetailLooknFeel.ShowFeedDetail>()
    val showEditOrShare = MutableLiveData<FeedDetailLooknFeel.ShowEditOrShareImage>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )

    }

    private fun handleOnCreate(onCreate: ActivityLifecycleState.OnCreate) {
        val feedDetailType = onCreate.intent.getStringExtra(Constants.EXTRA_KEY_FEED_DETAIL_TYPE)
        val feedId         = onCreate.intent.getIntExtra(Constants.EXTRA_KEY_FEED_ID, -1)

        if(feedId != -1) {
            appChannel.accept(AppData.RequestTo.Remote.FetchDetailFeed(feedId))
        }

        if(feedDetailType == FeedDetailActivity.TYPE_MY_FEED) {
            showEditOrShare.postValue(FeedDetailLooknFeel.ShowEditOrShareImage(R.drawable.ic_edit))
        } else {
            showEditOrShare.postValue(FeedDetailLooknFeel.ShowEditOrShareImage(R.drawable.ic_share))
        }
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.FeedDetailFetched -> {
                showFeedDetail.postValue(FeedDetailLooknFeel.ShowFeedDetail(remote.detailFeed))
            }
        }
    }

}
