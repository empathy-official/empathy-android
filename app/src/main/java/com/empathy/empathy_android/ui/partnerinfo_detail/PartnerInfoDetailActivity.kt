package com.empathy.empathy_android.ui.partnerinfo_detail

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class PartnerInfoDetailActivity: BaseActivity<PartnerInfoDetailViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_partner_info_detail
    override fun getViewModel(): Class<PartnerInfoDetailViewModel> = PartnerInfoDetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}