package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import com.empathy.empathy_android.repository.model.Partner
import com.empathy.empathy_android.repository.model.PartnerDetail

internal sealed class PartnerLooknFeel {

    data class ShowPartnerInfo(val partner: Partner): PartnerLooknFeel()

}
