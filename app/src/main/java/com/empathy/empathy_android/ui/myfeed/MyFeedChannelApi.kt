package com.empathy.empathy_android.ui.myfeed

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.Observable

internal interface MyFeedChannelApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<MyFeedViewAction>
    fun ofNavigation(): Observable<MyFeedNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: MyFeedViewAction)
    fun accept(navigation: MyFeedNavigation)
}
