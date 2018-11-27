package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.repository.model.LocationEnum
import com.skt.Tmap.TMapData
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal interface LoginViewModel {

    class ViewModel @Inject constructor(
                            val channel: LoginChannelApi,
                    private val appChannel: AppChannelApi,
         @LocFilter private var locationFilter: LocationFilterApi

    ): BaseViewModel() {

        private val onViewAction = channel.ofViewAction()

        init {
            compositeDisposable.addAll(
                    onViewAction
                            .subscribeOn(Schedulers.io())
                            .subscribeBy(
                                    onNext = ::handleOnViewAction
                            )
            )
        }

        private fun handleOnViewAction(loginViewAction: LoginViewAction) {
            when(loginViewAction) {
                is LoginViewAction.OnLocationChange -> {
                    val address = TMapData().convertGpsToAddress(loginViewAction.latitude, loginViewAction.longtitude)

                    val filteredAddress = when {
                        address.contains("서울")    -> "Seoul"
                        address.contains("경기도")   -> "GyeonggiDo"
                        address.contains("인천")    -> "Incheon"
                        address.contains("대전")    -> "Daejeon"
                        address.contains("대구")    -> "Daegue"
                        address.contains("광주")    -> "Gwangju"
                        address.contains("부산")    -> "Busan"
                        address.contains("울산")    -> "Ulsan"
                        address.contains("강원도")   -> "GangwonDo"
                        address.contains("충청북도") -> "ChungcheongbukDo"
                        address.contains("충청남도") -> "ChungcheongnamDo"
                        address.contains("경상북도") -> "GyeongsangbukDo"
                        address.contains("경상남도") -> "GyeongsangnamDo"
                        address.contains("전라북도") -> "Jeollabukdo"
                        address.contains("전라남도") -> "JeollanamDo"
                        address.contains("제주도")  -> "Jejudo"
                        else                      -> ""
                    }

                    locationFilter.setLocationAddress(filteredAddress)
                }
            }
        }
    }

}
