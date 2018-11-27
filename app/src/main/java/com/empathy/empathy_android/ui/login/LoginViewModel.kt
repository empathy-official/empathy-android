package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.skt.Tmap.TMapData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
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
                            .subscribeBy(
                                    onNext = ::handleOnViewAction
                            )
            )
        }

        private fun handleOnViewAction(loginViewAction: LoginViewAction) {
            when(loginViewAction) {
                is LoginViewAction.OnLocationChange -> {

                    compositeDisposable.add(
                            Observable
                                    .fromCallable {
                                        TMapData().convertGpsToAddress(loginViewAction.latitude, loginViewAction.longtitude)
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
                                            else                        -> ""
                                        }

                                        locationFilter.setLocationAddress(filteredAddress)
                                    }
                    )
                }
            }
        }
    }

}
