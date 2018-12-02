package com.empathy.empathy_android.ui.partnerinfo

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.replaceFragment
import com.empathy.empathy_android.extensions.replaceFragmentWithourAnimation
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.ui.partnerinfo.partnerfragment.PartnerFragment
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationFragment
import com.empathy.empathy_android.utils.OnSwipeTouchListener
import java.util.*


internal class PartnerInfoActivity: BaseActivity<PartnerInfoViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_partner_info
    override fun getViewModel(): Class<PartnerInfoViewModel> = PartnerInfoViewModel::class.java

    private var user: LocalUser? = null
    private var prevIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra(Constants.EXTRA_KEY_USER) as LocalUser

        initializeFragment(Random().nextInt(2))
    }

    private fun initializeFragment(num: Int) {
        when(num) {
            0 -> replaceFragmentWithourAnimation(TourOrganizationFragment.newInstance(user!!).apply {
                setOnSwipeListener(object : OnSwipeTouchListener.OnViewTransitionListener {
                    override fun viewTransitioned() {
                        replacePartnerFragment()
                    }
                })

                prevIndex = num
            }, "tour", R.id.partner_info_container)

            1 -> replaceFragmentWithourAnimation(PartnerFragment.newInstance().apply {
                setOnSwipeListener(object : OnSwipeTouchListener.OnViewTransitionListener {
                    override fun viewTransitioned() {
                        replacePartnerFragment()
                    }
                })

                prevIndex = num
            }, "partner", R.id.partner_info_container)
        }
    }

    private fun startPartnerFragment(num: Int) {
        when(num) {
            0 -> replaceFragment(TourOrganizationFragment.newInstance(user!!).apply {
                setOnSwipeListener(object : OnSwipeTouchListener.OnViewTransitionListener {
                    override fun viewTransitioned() {
                        replacePartnerFragment()
                    }
                })

                prevIndex = num
            }, "tour", R.id.partner_info_container)

            1 -> replaceFragment(PartnerFragment.newInstance().apply {
                setOnSwipeListener(object : OnSwipeTouchListener.OnViewTransitionListener {
                    override fun viewTransitioned() {
                        replacePartnerFragment()
                    }
                })

                prevIndex = num
            }, "partner", R.id.partner_info_container)
        }
    }

    private fun replacePartnerFragment() {
        while(true) {
            val randomIndex = Random().nextInt(2)

            if(prevIndex != randomIndex) {
                startPartnerFragment(randomIndex)

                break
            }
        }
    }


}