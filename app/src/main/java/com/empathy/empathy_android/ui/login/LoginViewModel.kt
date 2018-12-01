package com.empathy.empathy_android.ui.login

import android.util.Log
import com.empathy.empathy_android.BaseViewModel
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.repository.model.LocationEnum
import com.empathy.empathy_android.repository.model.User
import com.facebook.Profile
import com.skt.Tmap.TMapData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.URL
import javax.inject.Inject

internal interface LoginViewModel {

    class ViewModel @Inject constructor(
                            val channel: LoginChannelApi,
                    private val appChannel: AppChannelApi,
         @LocFilter private var locationFilter: LocationFilterApi

    ): BaseViewModel() {

        private val onViewAction = channel.ofViewAction()

        private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

        private var user: User = User()
        private var address = ""

        private var latitude   = Constants.DEFAULT_LATITUDE
        private var longtitude = Constants.DEFAULT_LONGTITUDE

        init {
            compositeDisposable.addAll(
                    onViewAction.subscribeBy(onNext = ::handleOnViewAction),

                    onRemote.subscribeBy(onNext = ::handleOnRemote)
            )
        }

        private fun handleOnRemote(remote: AppData.RespondTo.Remote) {
            when(remote) {
                is AppData.RespondTo.Remote.UserCreated -> {
                    val user = LocalUser(user.name, user.profileUrl, address, remote.userId, locationFilter.getLocationEnum() ?: LocationEnum.Seoul, latitude, longtitude)

                    channel.accept(LoginNavigation.LoginSuccess(user))
                }
            }
        }

        private fun handleOnViewAction(loginViewAction: LoginViewAction) {
            when(loginViewAction) {
                is LoginViewAction.OnLocationChange -> {

                    latitude   = loginViewAction.latitude
                    longtitude = loginViewAction.longtitude

                    compositeDisposable.add(
                            Observable
                                    .fromCallable {
                                        TMapData().convertGpsToAddress(latitude, longtitude)
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

                                        address = it

                                        locationFilter.setLocationAddress(filteredAddress)
                                    }
                    )
                }

                is LoginViewAction.LoginClick -> {
                    val profile = Profile.getCurrentProfile()

                    val userToken = loginViewAction.token
                    val userid = profile.id
                    val username = profile.name
                    val userimage = URL("https://graph.facebook.com/$userid/picture?type=large").toString()
                    val loginType = "facebook"

                    user = User(username, loginType, userimage, userToken)

                    appChannel.accept(AppData.RequestTo.Remote.CreateUser(user))
                }
            }
        }
    }

}
