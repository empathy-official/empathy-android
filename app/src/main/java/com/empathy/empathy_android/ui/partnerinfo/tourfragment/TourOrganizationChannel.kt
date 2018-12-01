package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class TourOrganizationChannel @Inject constructor(): TourOrganizationChannelApi {

    private val fragmentChannel: Relay<FragmentLifeCycle>            = PublishRelay.create()
    private val viewActionChannel: Relay<TourOrganizationViewAction> = PublishRelay.create()
    private val navigationChannel: Relay<TourOrganizationNavigation> = PublishRelay.create()

    override fun ofLifeCycle(): Observable<FragmentLifeCycle>           = fragmentChannel
    override fun ofViewAction(): Observable<TourOrganizationViewAction> = viewActionChannel
    override fun ofNavigation(): Observable<TourOrganizationNavigation> = navigationChannel

    override fun accept(lifecycle: FragmentLifeCycle)           = fragmentChannel.accept(lifecycle)
    override fun accept(viewAction: TourOrganizationViewAction) = viewActionChannel.accept(viewAction)
    override fun accept(navigation: TourOrganizationNavigation) = navigationChannel.accept(navigation)

}