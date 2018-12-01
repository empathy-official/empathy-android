package com.empathy.empathy_android.ui.myfeed

import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.Observable

internal interface MyFeedChannelApi {

    fun ofLifeCycle(): Observable<LifecycleState>
    fun ofViewAction(): Observable<MyFeedViewAction>
    fun ofNavigation(): Observable<MyFeedNavigation>

    fun accept(lifecycle: LifecycleState)
    fun accept(viewAction: MyFeedViewAction)
    fun accept(navigation: MyFeedNavigation)
}
