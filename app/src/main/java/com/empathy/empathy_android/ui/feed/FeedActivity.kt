package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.mypage.MyFeedActivity
import com.empathy.empathy_android.ui.partnerinfo.PartnerInfoActivity
import com.empathy.empathy_android.ui.tmap.MapActivity
import com.empathy.empathy_android.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.activity_feed.*

internal class FeedActivity : BaseActivity<FeedViewModel>() {

    private val logAdapter = FeedRecyclerAdapter()

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel> = FeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(LifecycleState.OnCreate(intent, savedInstanceState))

        subscribeLooknFeel()

        initializeView()
        initializeListener()
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showFeeds, ::handleShowFeeds)
    }

    private fun handleShowFeeds(looknFeel: FeedLooknFeel.ShowFeeds) {
        logAdapter.setFeeds(looknFeel.feeds)
    }

    private fun initializeView() {
        with(log_recycler) {
            layoutManager = LinearLayoutManager(this@FeedActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter       = logAdapter
        }
    }

    private fun initializeListener() {
        constLayout.setOnTouchListener (OnSwipeTouchListener(this@FeedActivity))

        my_feed_container.setOnClickListener {
            startActivity(Intent(this, MyFeedActivity::class.java))
        }

        info_container.setOnClickListener {
            startActivity(Intent(this, PartnerInfoActivity::class.java))
        }

        filter_location.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

}
