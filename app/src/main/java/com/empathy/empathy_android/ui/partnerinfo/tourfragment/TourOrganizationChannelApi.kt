package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import io.reactivex.Observable


internal interface TourOrganizationChannelApi {

    fun ofLifeCycle(): Observable<FragmentLifeCycle>
    fun ofViewAction(): Observable<TourOrganizationViewAction>
    fun ofNavigation(): Observable<TourOrganizationNavigation>

    fun accept(lifecycle: FragmentLifeCycle)
    fun accept(viewAction: TourOrganizationViewAction)
    fun accept(navigation: TourOrganizationNavigation)
}