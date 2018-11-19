package com.empathy.empathy_android.ui.feeddetail

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class FeedDetailActivity: BaseActivity<FeedDetailViewModel.ViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_feed_detail
    override fun getViewModel(): Class<FeedDetailViewModel.ViewModel> = FeedDetailViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}