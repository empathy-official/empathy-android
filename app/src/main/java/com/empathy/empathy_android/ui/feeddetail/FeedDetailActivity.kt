package com.empathy.empathy_android.ui.feeddetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

        initializeView()
        initializeListener()
    }

    private fun initializeView() {}

    private fun subscribeLooknFeel() {
        observe(viewModel.showFeedDetail, ::handleFeedDetail)
        observe(viewModel.showEditOrShare, ::handleEditOrShareImage)
//        observe(viewModel.showEditableMode, ::handleEditableMode)
    }

    private fun handleEditableMode(showEditableMode: FeedDetailLooknFeel.ShowEditableMode?) {
        feed_title.isEnabled = true
        content.isEnabled = true
    }

    private fun handleFeedDetail(looknFeel: FeedDetailLooknFeel.ShowFeedDetail) {
        val feedDetail = looknFeel.feedDetail

        feed_detail_img.loadImage(feedDetail.imageUrl)
        profile_image.loadImage(feedDetail.ownerProfileUrl)

        date.text       = feedDetail.creationTime
        address.text    = feedDetail.location

        feed_title.setText(feedDetail.title)
        content.setText(feedDetail.contents)
    }

    private fun handleEditOrShareImage(looknFeel: FeedDetailLooknFeel.ShowEditOrShareImage) {
        edit_or_share.setImageResource(looknFeel.imageResource)
    }

    private fun initializeListener() {
        edit_or_share.setOnClickListener {
            viewModel.channel.accept(FeedDetailViewAction.EditOrShareClicked)

            //TODO: share 하는 부분이 여기임
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                type = "image/*"
//
//                putExtra(Intent.EX, )
//            }
//
//            startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
        }
    }
}