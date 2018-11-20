package com.empathy.empathy_android.ui.feedinput

import com.empathy.empathy_android.appdata.LifecycleState
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

internal class FeedInputChannel: FeedInputChanneApi {

    private val lifecycleChannel: Relay<LifecycleState>       = PublishRelay.create()
    private val viewActionChannel: Relay<FeedInputViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<FeedInputNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<LifecycleState>       = lifecycleChannel
    override fun ofViewAction(): Observable<FeedInputViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<FeedInputNavigation> = navigationChannel

    override fun accept(lifecycle: LifecycleState)       = lifecycleChannel.accept(lifecycle)
    override fun accept(viewAction: FeedInputViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: FeedInputNavigation) = navigationChannel.accept(navigation)


}