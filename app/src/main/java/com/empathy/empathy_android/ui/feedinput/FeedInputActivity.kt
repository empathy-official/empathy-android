package com.empathy.empathy_android.ui.feedinput

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R

internal class FeedInputActivity: BaseActivity<FeedInputViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_feed_input
    override fun getViewModel(): Class<FeedInputViewModel.ViewModel> = FeedInputViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}