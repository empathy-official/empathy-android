package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


internal class PartnerViewModel @Inject constructor(
                val channel: PartnerChannelApi,
        private val appChannel: AppChannelApi

): BaseViewModel() {

    private val onActivityCreated = channel.ofLifeCycle().ofType(FragmentLifeCycle.OnActivityCreated::class.java)

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    val showPartnerInfo = MutableLiveData<PartnerLooknFeel.ShowPartnerInfo>()

    init {
        compositeDisposable.addAll(
                onActivityCreated.subscribeBy(onNext = ::handleOnActivityCreated),
                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.PartnerInfoFetched -> {
                showPartnerInfo.postValue(PartnerLooknFeel.ShowPartnerInfo(remote.partner))
            }
        }
    }

    private fun handleOnActivityCreated(onActivityCreated: FragmentLifeCycle.OnActivityCreated) {
        appChannel.accept(AppData.RequestTo.Remote.FetchPartnerInfo)
    }

}