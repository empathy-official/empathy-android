package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.feeddetail.FeedDetailActivity
import com.empathy.empathy_android.ui.mypage.MyFeedsActivity
import com.empathy.empathy_android.ui.partnerinfo.PartnerInfoActivity
import com.empathy.empathy_android.ui.tmap.MapActivity
import com.empathy.empathy_android.utils.OnSwipeTouchListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_feed.*

internal class FeedActivity : BaseActivity<FeedViewModel>() {

    private val logAdapter = FeedRecyclerAdapter()

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel> = FeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(LifecycleState.OnCreate(intent, savedInstanceState))

        subscribeLooknFeel()
        subscribeNavigation()

        initializeView()
        initializeListener()
    }

    private fun subscribeNavigation() {
        viewModel.channel
                .ofNavigation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    when(it) {
                        is FeedNavigation.NavigateToFeed -> {
                            startActivity(Intent(this, MyFeedsActivity::class.java).apply {
                                putExtra(Constants.EXTRA_KEY_USER, it.user)
                            })
                        }
                    }
                }
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showLocation, ::handleShowLocation)
        observe(viewModel.showFeeds, ::handleShowFeeds)
        observe(viewModel.showFeedPlaceholder, ::handleShowFeedPlaceholder)
        observe(viewModel.showMyFeed, ::handleShowMyFeed)
        observe(viewModel.showSubContent, ::handleShowSubContent)
    }

    private fun handleShowLocation(looknFeel: FeedLooknFeel.ShowLocation) {
        toolbar.text = looknFeel.location
    }

    private fun handleShowFeedPlaceholder(looknFeel: FeedLooknFeel.ShowFeedPlaceHolder) {
        default_title.visibility = View.VISIBLE
        my_feed_container.loadImage(looknFeel.placeholderImage)
        default_content.text = looknFeel.content
        default_content.visibility = View.VISIBLE
    }

    private fun handleShowMyFeed(looknFeel: FeedLooknFeel.ShowMyFeed) {
        my_feed_title.visibility = View.VISIBLE
        my_feed_container.loadImage(looknFeel.myFeedImage)
        my_feed_content.text = looknFeel.content
        my_feed_content.visibility = View.VISIBLE
    }

    private fun handleShowSubContent(looknFeel: FeedLooknFeel.ShowSubContent) {
        sub_content.text = looknFeel.subContent
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
            viewModel.channel.accept(FeedViewAction.NavigateToMyFeedClicked)
        }

        info_container.setOnClickListener {
            startActivity(Intent(this, PartnerInfoActivity::class.java))
        }

        filter_location.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        logAdapter.setOnItemClickListener(object : FeedRecyclerAdapter.ItemClickListener {
            override fun onItemClicked(feedId: Int?) {
                startActivity(Intent(this@FeedActivity, FeedDetailActivity::class.java).apply {
                    putExtra(Constants.EXTRA_KEY_FEED_DETAIL_TYPE, FeedDetailActivity.TYPE_OTHER_FEED)
                    putExtra(Constants.EXTRA_KEY_FEED_ID, feedId)
                })
            }
        })
    }

}
