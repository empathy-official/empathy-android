package com.empathy.empathy_android.ui.partnerinfo.tourfragment

internal sealed class TourOrganizationViewAction {

    data class NavigateToDetailClicked(val targetId: String, val contentType: String): TourOrganizationViewAction()

}
