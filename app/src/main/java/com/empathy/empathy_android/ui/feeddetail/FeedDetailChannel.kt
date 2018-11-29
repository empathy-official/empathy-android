package com.empathy.empathy_android.ui.feeddetail

import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable


internal class FeedDetailChannel: FeedDetailChannelApi {

    private val lifecycleChannel: Relay<LifecycleState>        = PublishRelay.create()
    private val viewActionChannel: Relay<FeedDetailViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<FeedDetailNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<LifecycleState>        = lifecycleChannel
    override fun ofViewAction(): Observable<FeedDetailViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<FeedDetailNavigation> = navigationChannel

    override fun accept(lifecycle: LifecycleState)        = lifecycleChannel.accept(lifecycle)
    override fun accept(viewAction: FeedDetailViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: FeedDetailNavigation) = navigationChannel.accept(navigation)

}