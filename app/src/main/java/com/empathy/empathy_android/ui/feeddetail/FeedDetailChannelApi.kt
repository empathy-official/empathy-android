package com.empathy.empathy_android.ui.feeddetail

import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.Observable

internal interface FeedDetailChannelApi {

    fun ofLifeCycle(): Observable<LifecycleState>
    fun ofViewAction(): Observable<FeedDetailViewAction>
    fun ofNavigation(): Observable<FeedDetailNavigation>

    fun accept(lifecycle: LifecycleState)
    fun accept(viewAction: FeedDetailViewAction)
    fun accept(navigation: FeedDetailNavigation)

}
