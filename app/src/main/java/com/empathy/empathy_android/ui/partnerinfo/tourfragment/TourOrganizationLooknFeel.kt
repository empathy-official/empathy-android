package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import com.empathy.empathy_android.repository.model.TourInfo

internal sealed class TourOrganizationLooknFeel {

    data class ShowTourInfos(val tourInfos: MutableList<TourInfo>): TourOrganizationLooknFeel()

}
