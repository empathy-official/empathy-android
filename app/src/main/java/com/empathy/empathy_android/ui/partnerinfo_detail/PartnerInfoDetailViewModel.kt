package com.empathy.empathy_android.ui.partnerinfo_detail

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.ui.partnerinfo.partnerfragment.PartnerChannelApi
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class PartnerInfoDetailViewModel @Inject constructor(
        val channel: PartnerInfoDetailChannelApi,
        private val appChannel: AppChannelApi
): BaseViewModel() {

    private val onCreate = channel.ofLifeCycle().ofType(ActivityLifecycleState.OnCreate::class.java)
    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    val showTourDetail = MutableLiveData<PartnerDetailLooknFeel.ShowTourDetail>()
    val showPartnerDetail = MutableLiveData<PartnerDetailLooknFeel.ShowPartnerDetail>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),
                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.TourInfoDetailFetched -> {
                val tourDetail = remote.tourDetail

                showTourDetail.postValue(PartnerDetailLooknFeel.ShowTourDetail(tourDetail))
            }

            is AppData.RespondTo.Remote.PartnerInfoDetailFetched -> {
                val partnerDetail = remote.partnerDetail

                showPartnerDetail.postValue((PartnerDetailLooknFeel.ShowPartnerDetail(partnerDetail)))
            }
        }
    }

    private fun handleOnCreate(onCreate: ActivityLifecycleState.OnCreate) {
        val intent = onCreate.intent

        intent.getStringExtra(Constants.EXTRA_KEY_PARTNER_INFO_DETAIL_TYPE).let {
            if(it == PartnerInfoDetailActivity.TYPE_PARTNER) {
                val partnerId = intent.getStringExtra(Constants.EXTRA_KEY_PARTNER_ID) ?: ""

                appChannel.accept(AppData.RequestTo.Remote.FetchPartnerInfoDetail(partnerId))
            } else if(it == PartnerInfoDetailActivity.TYPE_TOUR) {
                val targetId = intent.getStringExtra(Constants.EXTRA_KEY_TARGET_ID) ?: ""
                val contentType = intent.getStringExtra(Constants.EXTRA_KEY_CONTENT_TYPE) ?: ""

                appChannel.accept(AppData.RequestTo.Remote.FetchTourInfoDetail(contentType, targetId))
            }
        }
    }

}
