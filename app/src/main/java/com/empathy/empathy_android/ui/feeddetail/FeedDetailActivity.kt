package com.empathy.empathy_android.ui.feeddetail

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import kotlinx.android.synthetic.main.activity_feed_detail.*


internal class FeedDetailActivity: BaseActivity<FeedDetailViewModel>() {

    companion object {
        const val TYPE_MY_FEED    = "extra_key_feed_type_my_feed"
        const val TYPE_OTHER_FEED = "extra_key_feed_type_other_feed"
    }

    override fun getLayoutRes(): Int = R.layout.activity_feed_detail
    override fun getViewModel(): Class<FeedDetailViewModel> = FeedDetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(ActivityLifecycleState.OnCreate(intent, savedInstanceState))

        subscribeLooknFeel()
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showFeedDetail, ::handleFeedDetail)
        observe(viewModel.showEditOrShare, ::handleEditOrShareImage)
    }

    private fun handleFeedDetail(looknFeel: FeedDetailLooknFeel.ShowFeedDetail) {
        val feedDetail = looknFeel.feedDetail

        feed_detail_img.loadImage(feedDetail.imageUrl)
        profile_image.loadImage(feedDetail.ownerProfileUrl)

        date.text       = feedDetail.creationTime
        feed_title.text = feedDetail.title
        content.text    = feedDetail.contents
        address.text    = feedDetail.location
    }

    private fun handleEditOrShareImage(looknFeel: FeedDetailLooknFeel.ShowEditOrShareImage) {
        edit_or_share.setImageResource(looknFeel.imageResource)
    }
}