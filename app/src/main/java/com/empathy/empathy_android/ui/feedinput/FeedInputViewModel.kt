package com.empathy.empathy_android.ui.feedinput

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.mypage.MyFeedsActivity
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal interface FeedInputViewModel {

    class ViewModel @Inject constructor(
            private val appChannel: AppChannelApi,
                    val channel: FeedInputChanneApi

    ): BaseViewModel() {

        val showFeedInputImage = MutableLiveData<FeedInputLooknFeel.ShowFeedInputImage>()

        private val onCreateObserver = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

        init {
            addDisposables(
                    onCreateObserver
                            .subscribeBy(onNext = ::handleOnCreate)
            )
        }

        private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
            val uri = onCreate.intent.getStringExtra(MyFeedsActivity.EXTRA_KEY_FEED_IMAGE_URI)

            val imageUri = Uri.parse(uri)

            showFeedInputImage.value = FeedInputLooknFeel.ShowFeedInputImage(imageUri)
        }


    }
}
