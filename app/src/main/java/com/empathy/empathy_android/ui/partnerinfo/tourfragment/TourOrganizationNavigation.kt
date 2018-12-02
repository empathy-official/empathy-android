package com.empathy.empathy_android.ui.partnerinfo.tourfragment

internal sealed class TourOrganizationNavigation {

    data class NavigateToTourDetail(val contentType: Int, val targetId: String): TourOrganizationNavigation()

    object NavigateToDetailClicked: TourOrganizationNavigation()

}
