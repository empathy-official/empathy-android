package com.empathy.empathy_android.ui.partnerinfo_detail

import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import io.reactivex.Observable


internal interface PartnerInfoDetailChannelApi {

    fun ofLifeCycle(): Observable<FragmentLifeCycle>
    fun ofViewAction(): Observable<PartnerInfoDetailViewAction>
    fun ofNavigation(): Observable<PartnerInfoDetailNavigation>

    fun accept(lifecycle: FragmentLifeCycle)
    fun accept(viewAction: PartnerInfoDetailViewAction)
    fun accept(navigation: PartnerInfoDetailNavigation)

}