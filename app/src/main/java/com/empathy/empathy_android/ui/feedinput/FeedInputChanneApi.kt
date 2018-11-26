package com.empathy.empathy_android.ui.feedinput

import com.empathy.empathy_android.http.appchannel.LifecycleState
import io.reactivex.Observable

internal interface FeedInputChanneApi {

    fun ofLifeCycle(): Observable<LifecycleState>
    fun ofViewAction(): Observable<FeedInputViewAction>
    fun ofNavigation(): Observable<FeedInputNavigation>

    fun accept(lifecycle: LifecycleState)
    fun accept(viewAction: FeedInputViewAction)
    fun accept(navigation: FeedInputNavigation)

}