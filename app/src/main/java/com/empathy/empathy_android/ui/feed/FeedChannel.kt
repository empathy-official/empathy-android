package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject

internal class FeedChannel @Inject constructor(): FeedChannelApi {

    private val lifecycleChannel: Relay<LifecycleState>  = PublishRelay.create()
    private val viewActionChannel: Relay<FeedViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<FeedNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<LifecycleState>  = lifecycleChannel
    override fun ofViewAction(): Observable<FeedViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<FeedNavigation> = navigationChannel

    override fun accept(lifecycle: LifecycleState)  = lifecycleChannel.accept(lifecycle)
    override fun accept(viewAction: FeedViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: FeedNavigation) = navigationChannel.accept(navigation)

}