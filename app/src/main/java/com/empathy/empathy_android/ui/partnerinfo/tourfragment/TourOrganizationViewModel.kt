package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import com.empathy.empathy_android.repository.model.LocalUser
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import javax.inject.Inject

internal class TourOrganizationViewModel @Inject constructor(
                val channel: TourOrganizationChannelApi,
        private val appChannel: AppChannelApi

): BaseViewModel() {

    companion object {
//        관광지 > contentTypeId=12
//        // 문화시설 > contentTypeId=14
//        // 측제공연행사 > contentTypeId=15
//        // 음식점 >  contentTypeId=39
    }

    private var perPage = 1

    private val onViewCreated     = channel.ofLifeCycle().ofType(FragmentLifeCycle.OnViewCreated::class.java)
    private val onActivityCreated = channel.ofLifeCycle().ofType(FragmentLifeCycle.OnActivityCreated::class.java)
    private val onViewAction      = channel.ofViewAction()

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var contentType     = "12"
    private var user: LocalUser = LocalUser()

    val showTourInfos = MutableLiveData<TourOrganizationLooknFeel.ShowTourInfos>()

    init {
        compositeDisposable.addAll(
                onViewCreated.subscribeBy(onNext = ::handleOnViewCreated),
                onActivityCreated.subscribeBy(onNext = ::handleOnActivityCreated),
                onViewAction.subscribeBy(onNext = ::handleOnViewAction),
                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    private fun handleOnViewAction(tourOrganizationViewAction: TourOrganizationViewAction) {
        when(tourOrganizationViewAction) {
            is TourOrganizationViewAction.NavigateToDetailClicked -> {
                channel.accept(TourOrganizationNavigation.NavigateToTourDetail(contentType, tourOrganizationViewAction.targetId))
            }
        }
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.TourInfosFetched -> {
                showTourInfos.postValue(TourOrganizationLooknFeel.ShowTourInfos(remote.tourInfos))
            }
        }
    }

    private fun handleOnViewCreated(onViewCreated: FragmentLifeCycle.OnViewCreated) {
        user = onViewCreated.arguments?.getSerializable(TourOrganizationFragment.KEY_BUNDLE_USER) as LocalUser
    }

    private fun handleOnActivityCreated(onActivityCreated: FragmentLifeCycle.OnActivityCreated) {
        val contentType =  when(Random().nextInt(4)) {
            0    -> 12
            1    -> 14
            2    -> 15
            3    -> 39
            else -> 12
        }

        this.contentType = contentType.toString()

        appChannel.accept(AppData.RequestTo.Remote.FetchTourInfos(contentType, user.latitude, user.longtitude, Constants.DEFAULT_RANGE, perPage++))
    }

}


