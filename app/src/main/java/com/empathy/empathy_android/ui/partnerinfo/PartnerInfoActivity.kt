package com.empathy.empathy_android.ui.partnerinfo

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.replaceFragment
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.ui.partnerinfo.partnerfragment.PartnerFragment
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationFragment
import java.util.*


internal class PartnerInfoActivity: BaseActivity<PartnerInfoViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_partner_info
    override fun getViewModel(): Class<PartnerInfoViewModel> = PartnerInfoViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        when(Random().nextInt(2)) {
            0 -> replaceFragment(TourOrganizationFragment.newInstance(user), "tour", R.id.partner_info_container)
            1 -> replaceFragment(PartnerFragment.newInstance(), "partner", R.id.partner_info_container)
        }

    }

}