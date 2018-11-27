package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.Observable

internal interface FeedChannelApi {

    fun ofLifeCycle(): Observable<LifecycleState>
    fun ofViewAction(): Observable<FeedViewAction>
    fun ofNavigation(): Observable<FeedNavigation>

    fun accept(lifecycle: LifecycleState)
    fun accept(viewAction: FeedViewAction)
    fun accept(navigation: FeedNavigation)
}