package com.empathy.empathy_android.ui.feedinput

import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import io.reactivex.Observable

internal interface FeedInputChanneApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<FeedInputViewAction>
    fun ofNavigation(): Observable<FeedInputNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: FeedInputViewAction)
    fun accept(navigation: FeedInputNavigation)

}