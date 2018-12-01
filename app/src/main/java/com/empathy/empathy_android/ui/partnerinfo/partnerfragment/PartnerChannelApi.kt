package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import io.reactivex.Observable

internal interface PartnerChannelApi {

    fun ofLifeCycle(): Observable<FragmentLifeCycle>
    fun ofViewAction(): Observable<PartnerViewAction>
    fun ofNavigation(): Observable<PartnerNavigation>

    fun accept(lifecycle: FragmentLifeCycle)
    fun accept(viewAction: PartnerViewAction)
    fun accept(navigation: PartnerNavigation)

}
