package com.empathy.empathy_android.ui.partner

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.replaceFragment

internal class PartnerActivity: BaseActivity<PartnerViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_partner
    override fun getViewModel(): Class<PartnerViewModel.ViewModel> = PartnerViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(PartnerFragment.newInstance(0), "", R.id.frame)
    }
}