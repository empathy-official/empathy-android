package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.Observable


internal interface LoginChannelApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<LoginViewAction>
    fun ofNavigation(): Observable<LoginNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: LoginViewAction)
    fun accept(navigation: LoginNavigation)

}