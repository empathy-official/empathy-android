package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class PartnerChannel @Inject constructor(): PartnerChannelApi {

    private val fragmentChannel: Relay<FragmentLifeCycle>   = PublishRelay.create()
    private val viewActionChannel: Relay<PartnerViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<PartnerNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<FragmentLifeCycle>  = fragmentChannel
    override fun ofViewAction(): Observable<PartnerViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<PartnerNavigation> = navigationChannel

    override fun accept(lifecycle: FragmentLifeCycle)  = fragmentChannel.accept(lifecycle)
    override fun accept(viewAction: PartnerViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: PartnerNavigation) = navigationChannel.accept(navigation)

}