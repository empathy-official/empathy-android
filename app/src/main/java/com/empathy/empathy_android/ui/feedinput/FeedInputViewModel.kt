package com.empathy.empathy_android.ui.feedinput

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.ui.mypage.MyFeedsActivity
import io.reactivex.rxkotlin.subscribeBy
import java.io.File
import java.util.*
import javax.inject.Inject

internal class FeedInputViewModel @Inject constructor(
        private val appChannel: AppChannelApi,
                val channel: FeedInputChanneApi

): BaseViewModel() {

    private val onCreate     = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)
    private val onViewAction = channel.ofViewAction()

    val showFeedInputImage = MutableLiveData<FeedInputLooknFeel.ShowFeedInputImage>()
//    val showInputInfo      = MutableLiveData<FeedInputLooknFeel.ShowInputInfo>()

    var imageUri: Uri? = null

    init {
        addDisposables(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onViewAction.subscribeBy(onNext = ::handleViewAction)
        )
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        val uri = onCreate.intent.getStringExtra(Constants.EXTRA_KEY_FEED_IMAGE_URI)
        val user = onCreate.intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        val file = File(uri)
        val lastModDate = Date(file.lastModified())

        Log.d("zcq", lastModDate.toString())

//        showInputInfo.postValue(FeedInputLooknFeel.ShowInputInfo(user.address))

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
