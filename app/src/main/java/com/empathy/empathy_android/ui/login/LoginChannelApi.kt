package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.Observable


internal interface LoginChannelApi {

    fun ofLifeCycle(): Observable<LifecycleState>
    fun ofViewAction(): Observable<LoginViewAction>
    fun ofNavigation(): Observable<LoginNavigation>

    fun accept(lifecycle: LifecycleState)
    fun accept(viewAction: LoginViewAction)
    fun accept(navigation: LoginNavigation)

}