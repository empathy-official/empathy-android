package com.empathy.empathy_android.ui.feed

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.http.appchannel.LifecycleState
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

    private val onCreate = channel.ofLifeCycle().ofType(LifecycleState.OnCreate::class.java)

    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

    private var hasLocation = false

    val showFeeds = MutableLiveData<FeedLooknFeel.ShowFeeds>()

    init {
        compositeDisposable.addAll(
                onCreate.subscribeBy(
                        onNext = ::handleOnCreate
                ),

                onRemote.subscribeBy(
                        onNext = ::handleOnRemote
                )
        )
    }

    override fun onLocationChange(location: Location?) {
        val latitude   = location?.latitude
        val longtitude = location?.longitude

        compositeDisposable.add(
                Observable
                        .fromCallable {
                            TMapData().convertGpsToAddress(latitude!!, longtitude!!)
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

                            Log.d("LOCATION_FILTER11", locationFilter.getLocationEnum()?.location + " , " + locationFilter.getLocationEnum()?.code)
                        }
        )

        if (hasLocation == false) {
            appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter(locationFilter.getLocationEnum()!!))

            hasLocation = true
            tmapGpsManager.CloseGps()
        }
    }

    private fun handleOnCreate(onCreate: LifecycleState.OnCreate) {
        val locationAddr = locationFilter.getLocationEnum()?.location
        val locationCode = locationFilter.getLocationEnum()?.code

        if(locationAddr == null || locationAddr == "") {
            setGps()
        } else {
            Log.d("LOCATION_FILTER", locationFilter.getLocationEnum()?.location + " , " + locationFilter.getLocationEnum()?.code)

            appChannel.accept(AppData.RequestTo.Remote.FetchFeedsByLocationFilter(locationFilter.getLocationEnum()!!))

            hasLocation = true
        }
    }

    private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
        when(remote) {
            is AppData.RespondTo.Remote.FeedsByLocationFilterFetched -> {
                showFeeds.postValue(FeedLooknFeel.ShowFeeds(remote.feeds))
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


















