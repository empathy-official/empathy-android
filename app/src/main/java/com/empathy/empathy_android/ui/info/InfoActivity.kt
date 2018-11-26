package com.empathy.empathy_android.ui.info

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class InfoActivity: BaseActivity<InfoViewModel.ViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_info
    override fun getViewModel(): Class<InfoViewModel.ViewModel> = InfoViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}