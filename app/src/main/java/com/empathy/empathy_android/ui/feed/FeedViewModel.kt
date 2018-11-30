package com.empathy.empathy_android.ui.feed

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.di.qualifier.App
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.ui.login.LocationFilterApi
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class FeedViewModel @Inject constructor(
                           val channel: FeedChannelApi,
                   private val appChannel: AppChannelApi,
        @LocFilter private val locationFilter: LocationFilterApi,
        @LocFilter private val tmapView: TMapView,
        @LocFilter private val tmapGpsManager: TMapGpsManager

): BaseViewModel(), TMapGpsManager.onLocationChangedCallback  {

    companion object {
        private const val TRUE = "true"
    }

    private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var hasLocation = false
    private var user = LocalUser()

    val showLocation = MutableLiveData<FeedLooknFeel.ShowLocation>()
    val showFeeds = MutableLiveData<FeedLooknFeel.ShowFeeds>()
    val showFeedPlaceholder = MutableLiveData<FeedLooknFeel.ShowFeedPlaceHolder>()
    val showMyFeed = MutableLiveData<FeedLooknFeel.ShowMyFeed>()
    val showSubContent = MutableLiveData<FeedLooknFeel.ShowSubContent>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(onNext = ::handleOnCreate),

                onRemote.subscribeBy(onNext = ::handleOnRemote)
        )
    }

    override fun onLocationChange(location: Location?) {
        val latitude   = location?.latitude
        val longtitude = location?.longitude

        compositeDisposable.add(
                Observable
                        .fromCallable {
                            TMapData().convertGpsToAddress(latitude ?: 126.981106, longtitude ?: 37.568477)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            val filteredAddress = when {
                                it.contains("서울")    -> "Seoul"
                                it.contains("경기도")   -> "GyeonggiDo"
                                it.contains("인천")    -> "Incheon"
                                it.contains("대전")    -> "Daejeon"
                                it.contains("대구")    -> "Daegue"
                                it.contains("광주")    -> "Gwangju"
                                it.contains("부산")    -> "Busan"
                                it.contains("울산")    -> "Ulsan"
                                it.contains("강원도")   -> "GangwonDo"
                                it.contains("충청북도") -> "ChungcheongbukDo"
                                it.contains("충청남도") -> "ChungcheongnamDo"
                                it.contains("경상북도") -> "GyeongsangbukDo"
                                it.contains("경상남도") -> "GyeongsangnamDo"
                                it.contains("전라북도") -> "Jeollabukdo"
                                it.contains("전라남도") -> "JeollanamDo"
                                it.contains("제주도")  -> "Jejudo"
                                else                 -> ""
                            }

                            locationFilter.setLocationAddress(filteredAddress)
                        }
        )

        if (hasLocation == false) {
            locationFilter.getLocationEnum()?.let {
                appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter(user.userId.toString(), it))
            }

            hasLocation = true
            tmapGpsManager.CloseGps()
        }
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        user = onCreate.intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        val locationAddr = locationFilter.getLocationEnum()?.location
        val locationCode = locationFilter.getLocationEnum()?.code

        if(locationAddr == null || locationAddr == "") {
            setGps()
        } else {
            appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter(user.userId.toString(), locationFilter.getLocationEnum()!!))

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

    private fun setGps() {
        tmapView.apply {
            setSKTMapApiKey("b5be9a2e-d454-4177-8912-1d2c1cbee391")
            setTrackingMode(true)
            setSightVisible(true)
        }

        tmapGpsManager.provider    = TMapGpsManager.NETWORK_PROVIDER
        tmapGpsManager.minTime     = 100
        tmapGpsManager.minDistance = 1F

        tmapGpsManager.OpenGps()
    }

    override fun onCleared() {
        super.onCleared()

        tmapGpsManager.CloseGps()
    }
}


















