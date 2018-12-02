package com.empathy.empathy_android.ui.feedinput

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.EmpathyApp
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.empathy.empathy_android.repository.model.LocalUser
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import okhttp3.RequestBody
import android.provider.MediaStore
import androidx.loader.content.CursorLoader


internal class FeedInputViewModel @Inject constructor(
        private val appChannel: AppChannelApi,
                val channel: FeedInputChanneApi

): BaseViewModel() {

    private val onCreate     = channel.ofLifeCycle().ofType(ActivityLifecycleState.OnCreate::class.java)
    private val onViewAction = channel.ofViewAction()

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var imageUri: Uri? = null
    private var user           = LocalUser()

    val showFeedInputImage = MutableLiveData<FeedInputLooknFeel.ShowFeedInputImage>()
    val showInputInfo      = MutableLiveData<FeedInputLooknFeel.ShowInputInfo>()

    init {
        addDisposables(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onViewAction.subscribeBy(onNext = ::handleViewAction),

                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    private fun handleOnCreate(onCreate: ActivityLifecycleState.OnCreate) {
        val uri = onCreate.intent.getStringExtra(Constants.EXTRA_KEY_FEED_IMAGE_URI)
        user    = onCreate.intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        showInputInfo.postValue(FeedInputLooknFeel.ShowInputInfo(user.address, dateFormat.format(currentTime)))

        imageUri = Uri.parse(uri)
        imageUri?.let {
            showFeedInputImage.postValue(FeedInputLooknFeel.ShowFeedInputImage(it))
        }
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.FeedCreated -> {
                channel.accept(FeedInputNavigation.NavigateToMyFeed)
            }
        }
    }

    private fun handleViewAction(viewAction: FeedInputViewAction) {
        when(viewAction) {
            is FeedInputViewAction.SaveFeed -> {
                val title       = viewAction.title
                val description = viewAction.description

                if (!isValidEmail(title)) {
                    return
                }

                if (!isValidPassword(description)) {
                    return
                }

                val file = File(getPath(imageUri!!))
                val requestFile = RequestBody.create(
                        MediaType.parse(MediaType.parse("multipart/form-data").toString()),
                        file
                )
                val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val userId           = RequestBody.create(MediaType.parse("text/plain"), user.userId.toString())
                val parameterTitle   = RequestBody.create(MediaType.parse("text/plain"), title)
                val parameterDesc    = RequestBody.create(MediaType.parse("text/plain"), description)
                val address          = RequestBody.create(MediaType.parse("text/plain"), user.address)
                val userLocationEnum = RequestBody.create(MediaType.parse("text/plain"), user.userLocationEnum.toString())

                appChannel.accept(AppData.RequestTo.Remote.CreateFeed(userId, parameterTitle, parameterDesc, address, userLocationEnum, multipartBody))
            }
        }
    }

    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(EmpathyApp.instance, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        cursor?.moveToFirst()

        return cursor?.getString(column_index!!)!!
    }

    private fun isValidEmail(email: String?)
            = !email.isNullOrEmpty()

    private fun isValidPassword(password: String?)
            = !password.isNullOrEmpty()

}
