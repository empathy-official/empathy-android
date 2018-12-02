package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.Observable

internal interface FeedChannelApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<FeedViewAction>
    fun ofNavigation(): Observable<FeedNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: FeedViewAction)
    fun accept(navigation: FeedNavigation)
}