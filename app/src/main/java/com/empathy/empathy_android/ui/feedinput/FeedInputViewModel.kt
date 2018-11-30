package com.empathy.empathy_android.ui.feedinput

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.mypage.MyFeedsActivity
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class FeedInputViewModel @Inject constructor(
        private val appChannel: AppChannelApi,
                val channel: FeedInputChanneApi

): BaseViewModel() {

    private val onCreate     = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)
    private val onViewAction = channel.ofViewAction()

    val showFeedInputImage = MutableLiveData<FeedInputLooknFeel.ShowFeedInputImage>()

    var imageUri: Uri? = null

    init {
        addDisposables(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onViewAction.subscribeBy(onNext = ::handleViewAction)
        )
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        val uri = onCreate.intent.getStringExtra(MyFeedsActivity.EXTRA_KEY_FEED_IMAGE_URI)

        imageUri = Uri.parse(uri)

        imageUri?.let {
            showFeedInputImage.value = FeedInputLooknFeel.ShowFeedInputImage(it)
        }
    }

    private fun handleViewAction(viewAction: FeedInputViewAction) {
        when(viewAction) {
            is FeedInputViewAction.SaveFeed -> {
                val title       = viewAction.title
                val description = viewAction.description

                if(title != "" && description != "" && imageUri != null) {

                }
            }
        }
    }

}
