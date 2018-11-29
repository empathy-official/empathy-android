package com.empathy.empathy_android.ui.mypage

import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class MyFeedChannel @Inject constructor(): MyFeedChannelApi {

    private val lifecycleChannel: Relay<LifecycleState>    = PublishRelay.create()
    private val viewActionChannel: Relay<MyFeedViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<MyFeedNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<LifecycleState>    = lifecycleChannel
    override fun ofViewAction(): Observable<MyFeedViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<MyFeedNavigation> = navigationChannel

    override fun accept(lifecycle: LifecycleState)    = lifecycleChannel.accept(lifecycle)
    override fun accept(viewAction: MyFeedViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: MyFeedNavigation) = navigationChannel.accept(navigation)

}