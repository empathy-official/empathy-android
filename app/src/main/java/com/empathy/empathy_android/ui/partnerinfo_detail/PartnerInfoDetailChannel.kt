package com.empathy.empathy_android.ui.partnerinfo_detail

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationNavigation
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationViewAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class PartnerInfoDetailChannel @Inject constructor(): PartnerInfoDetailChannelApi {

    private val fragmentChannel: Relay<FragmentLifeCycle> = PublishRelay.create()
    private val viewActionChannel: Relay<PartnerInfoDetailViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<PartnerInfoDetailNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<FragmentLifeCycle>            = fragmentChannel
    override fun ofViewAction(): Observable<PartnerInfoDetailViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<PartnerInfoDetailNavigation> = navigationChannel

    override fun accept(lifecycle: FragmentLifeCycle)            = fragmentChannel.accept(lifecycle)
    override fun accept(viewAction: PartnerInfoDetailViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: PartnerInfoDetailNavigation) = navigationChannel.accept(navigation)

}