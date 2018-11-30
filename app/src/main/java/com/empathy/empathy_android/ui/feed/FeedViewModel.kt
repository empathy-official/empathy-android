package com.empathy.empathy_android.ui.feed

import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.ui.login.LocationFilterApi
import com.skt.Tmap.TMapData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class FeedViewModel @Inject constructor(
                           val channel: FeedChannelApi,
                   private val appChannel: AppChannelApi,
        @LocFilter private val locationFilter: LocationFilterApi

): BaseViewModel() {

    companion object {
        private const val TRUE = "true"
    }

    private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)
    private val onViewAction = channel.ofViewAction()

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var hasLocation = false
    private var user = LocalUser()

    val openGps = MutableLiveData<FeedLooknFeel.SetGps>()
    val closeGps = MutableLiveData<FeedLooknFeel.CloseGps>()
    val showLocation = MutableLiveData<FeedLooknFeel.ShowLocation>()
    val showFeeds = MutableLiveData<FeedLooknFeel.ShowFeeds>()
    val showFeedPlaceholder = MutableLiveData<FeedLooknFeel.ShowFeedPlaceHolder>()
    val showMyFeed = MutableLiveData<FeedLooknFeel.ShowMyFeed>()
    val showSubContent = MutableLiveData<FeedLooknFeel.ShowSubContent>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onRemote.subscribeBy(onNext = ::handleOnRemote),

                onViewAction.subscribeBy(onNext = ::handleOnViewAction)
        )
    }

//    override fun onLocationChange(location: Location?) {
//        val latitude   = location?.latitude
//        val longtitude = location?.longitude
//
//        compositeDisposable.add(
//                Observable
//                        .fromCallable {
//                            TMapData().convertGpsToAddress(latitude ?: Constants.DEFAULT_LATITUDE, longtitude ?: Constants.DEFAULT_LONGTITUDE)
//                        }
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeBy {
//                            val filteredAddress = when {
//                                it.contains("서울")    -> "Seoul"
//                                it.contains("경기도")   -> "GyeonggiDo"
//                                it.contains("인천")    -> "Incheon"
//                                it.contains("대전")    -> "Daejeon"
//                                it.contains("대구")    -> "Daegue"
//                                it.contains("광주")    -> "Gwangju"
//                                it.contains("부산")    -> "Busan"
//                                it.contains("울산")    -> "Ulsan"
//                                it.contains("강원도")   -> "GangwonDo"
//                                it.contains("충청북도") -> "ChungcheongbukDo"
//                                it.contains("충청남도") -> "ChungcheongnamDo"
//                                it.contains("경상북도") -> "GyeongsangbukDo"
//                                it.contains("경상남도") -> "GyeongsangnamDo"
//                                it.contains("전라북도") -> "Jeollabukdo"
//                                it.contains("전라남도") -> "JeollanamDo"
//                                it.contains("제주도")  -> "Jejudo"
//                                else                 -> ""
//                            }
//
//                            locationFilter.setLocationAddress(filteredAddress)
//                        }
//        )
//
//        if (hasLocation == false) {
//            locationFilter.getLocationEnum()?.let {
//                appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter("1", it))
//            }
//
//            hasLocation = true
////            tmapGpsManager.CloseGps()
//        }
//    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        user = onCreate.intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        val locationAddr = locationFilter.getLocationEnum()?.location
        val locationCode = locationFilter.getLocationEnum()?.code

        if(locationAddr == null || locationAddr == "") {
            openGps.postValue(FeedLooknFeel.SetGps)
//            openGps()
        } else {
//            user.userId.toString()
            locationFilter.getLocationEnum()?.let {
                appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter("1", it))
            }

            hasLocation = true
        }

        showLocation.postValue(FeedLooknFeel.ShowLocation(user.address))
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.FeedsByLocationFilterFetched -> {
                val feeds = remote.feedMain.otherPeopleList
                val isFirst = remote.feedMain.isFirst
                val mainText = remote.feedMain.mainText
                val imageUrl = remote.feedMain.imageURL

                val weekday = remote.feedMain.weekday
                val location = remote.feedMain.enumStr
                val subText = "행복한 날에"

                val subContent = weekday + "\n" + location + "\n" + subText

                if(isFirst == TRUE) {
                    showFeedPlaceholder.postValue(FeedLooknFeel.ShowFeedPlaceHolder(mainText, imageUrl))
                } else {
                    showMyFeed.postValue(FeedLooknFeel.ShowMyFeed(mainText, imageUrl))
                }

                showSubContent.postValue(FeedLooknFeel.ShowSubContent(subContent))
                showFeeds.postValue(FeedLooknFeel.ShowFeeds(feeds))
            }
        }
    }

    private fun handleOnViewAction(feedViewAction: FeedViewAction) {
        when(feedViewAction) {
            is FeedViewAction.NavigateToMyFeedClicked -> {
                channel.accept(FeedNavigation.NavigateToFeed(user))
            }

            is FeedViewAction.OnLocationChange -> {
                if(hasLocation) {
                    return
                }

                compositeDisposable.add(
                        Observable
                                .fromCallable {
                                    TMapData().convertGpsToAddress(feedViewAction.latitude, feedViewAction.longtitude)
                                }
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy {
                                    val filteredAddress = when {
                                        it.contains("서울") -> "Seoul"
                                        it.contains("경기도") -> "GyeonggiDo"
                                        it.contains("인천") -> "Incheon"
                                        it.contains("대전") -> "Daejeon"
                                        it.contains("대구") -> "Daegue"
                                        it.contains("광주") -> "Gwangju"
                                        it.contains("부산") -> "Busan"
                                        it.contains("울산") -> "Ulsan"
                                        it.contains("강원도") -> "GangwonDo"
                                        it.contains("충청북도") -> "ChungcheongbukDo"
                                        it.contains("충청남도") -> "ChungcheongnamDo"
                                        it.contains("경상북도") -> "GyeongsangbukDo"
                                        it.contains("경상남도") -> "GyeongsangnamDo"
                                        it.contains("전라북도") -> "Jeollabukdo"
                                        it.contains("전라남도") -> "JeollanamDo"
                                        it.contains("제주도") -> "Jejudo"
                                        else -> ""
                                    }

                                    locationFilter.setLocationAddress(filteredAddress)
                                }
                )

//                if (hasLocation == false) {
                locationFilter.getLocationEnum()?.let {
                    appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter("1", it))
                }

                hasLocation = true

                closeGps.postValue(FeedLooknFeel.CloseGps)
//                }
            }
        }
    }

//    private fun openGps() {
//        tmapView.apply {
//            setSKTMapApiKey("b5be9a2e-d454-4177-8912-1d2c1cbee391")
//            setTrackingMode(true)
//            setSightVisible(true)
//        }
//
//        tmapGpsManager.provider    = TMapGpsManager.NETWORK_PROVIDER
//        tmapGpsManager.minTime     = 100
//        tmapGpsManager.minDistance = 1F
//
//        tmapGpsManager.OpenGps()
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//
//        tmapGpsManager.CloseGps()
//    }
}