package com.empathy.empathy_android.ui.partnerinfo_detail

import com.empathy.empathy_android.repository.model.PartnerDetail
import com.empathy.empathy_android.repository.model.TourDetail

internal sealed class PartnerDetailLooknFeel {


    data class ShowTourDetail(val tourDetail: TourDetail): PartnerDetailLooknFeel()
    data class ShowPartnerDetail(val partnerDetail: PartnerDetail): PartnerDetailLooknFeel()

}
