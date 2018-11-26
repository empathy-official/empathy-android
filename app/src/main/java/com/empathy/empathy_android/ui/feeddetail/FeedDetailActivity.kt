package com.empathy.empathy_android.ui.feeddetail

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class FeedDetailActivity: BaseActivity<FeedDetailViewModel.ViewModel>() {

    companion object {
        const val EXTRA_KEY_FEED_TYPE_MY_FEED    = "extra_key_feed_type_my_feed"
        const val EXTRA_KEY_FEED_TYPE_OTHER_FEED = "extra_key_feed_type_other_feed"
    }

    override fun getLayoutRes(): Int = R.layout.activity_feed_detail
    override fun getViewModel(): Class<FeedDetailViewModel.ViewModel> = FeedDetailViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}