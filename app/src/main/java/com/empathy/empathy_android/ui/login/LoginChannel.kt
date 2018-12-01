package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class LoginChannel @Inject constructor(): LoginChannelApi {

    private val lifecycleChannel: Relay<ActivityLifecycleState>   = PublishRelay.create()
    private val viewActionChannel: Relay<LoginViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<LoginNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<ActivityLifecycleState>   = lifecycleChannel
    override fun ofViewAction(): Observable<LoginViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<LoginNavigation> = navigationChannel

    override fun accept(lifecycle: ActivityLifecycleState)   = lifecycleChannel.accept(lifecycle)
    override fun accept(viewAction: LoginViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: LoginNavigation) = navigationChannel.accept(navigation)

}