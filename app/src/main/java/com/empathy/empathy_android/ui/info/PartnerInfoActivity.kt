package com.empathy.empathy_android.ui.info

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class PartnerInfoActivity: BaseActivity<PartnerInfoViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_partner_info
    override fun getViewModel(): Class<PartnerInfoViewModel> = PartnerInfoViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = 0

        when(type) {
            0 -> {

            }
            1 -> {

            }
        }
    }

}