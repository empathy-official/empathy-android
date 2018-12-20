package com.empathy.empathy_android.ui.partnerinfo_detail

import android.app.Activity
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import io.reactivex.Observable


internal interface PartnerInfoDetailChannelApi {

    fun ofLifeCycle(): Observable<ActivityLifecycleState>
    fun ofViewAction(): Observable<PartnerInfoDetailViewAction>
    fun ofNavigation(): Observable<PartnerInfoDetailNavigation>

    fun accept(lifecycle: ActivityLifecycleState)
    fun accept(viewAction: PartnerInfoDetailViewAction)
    fun accept(navigation: PartnerInfoDetailNavigation)

}