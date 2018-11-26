package com.empathy.empathy_android.ui.feedinput

import android.os.Bundle
import android.provider.MediaStore
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.extensions.observe
import kotlinx.android.synthetic.main.activity_feed_input.*

internal class FeedInputActivity: BaseActivity<FeedInputViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_feed_input
    override fun getViewModel(): Class<FeedInputViewModel.ViewModel> = FeedInputViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLooknFeel()

        viewModel.channel.accept(LifecycleState.OnCreate(intent, savedInstanceState))
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showFeedInputImage, ::handleShowFeedInputImage)
    }

    private fun handleShowFeedInputImage(looknfeel: FeedInputLooknFeel.ShowFeedInputImage) {
        val feedInputImageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, looknfeel.imageUri)

        feed_input_image.setImageBitmap(feedInputImageBitmap)
    }

}