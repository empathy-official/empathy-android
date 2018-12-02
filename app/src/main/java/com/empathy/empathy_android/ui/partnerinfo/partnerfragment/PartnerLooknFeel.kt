package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import com.empathy.empathy_android.repository.model.Partner

internal sealed class PartnerLooknFeel {

    data class ShowPartnerInfo(val partner: Partner): PartnerLooknFeel()

}
