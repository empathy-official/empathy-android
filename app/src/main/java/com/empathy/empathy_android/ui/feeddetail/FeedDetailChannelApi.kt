package com.empathy.empathy_android.ui.feeddetail

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.Observable

internal interface FeedDetailChannelApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<FeedDetailViewAction>
    fun ofNavigation(): Observable<FeedDetailNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: FeedDetailViewAction)
    fun accept(navigation: FeedDetailNavigation)

}
