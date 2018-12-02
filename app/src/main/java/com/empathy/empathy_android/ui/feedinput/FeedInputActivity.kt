package com.empathy.empathy_android.ui.feedinput

import android.os.Bundle
import android.provider.MediaStore
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.empathy.empathy_android.extensions.observe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_feed_input.*

internal class FeedInputActivity: BaseActivity<FeedInputViewModel>() {

    private val compositeDisposable = CompositeDisposable()

    override fun getLayoutRes(): Int = R.layout.activity_feed_input
    override fun getViewModel(): Class<FeedInputViewModel> = FeedInputViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLooknFeel()
        subscribeNavigation()

        initializeListener()

        viewModel.channel.accept(ActivityLifecycleState.OnCreate(intent, savedInstanceState))
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    private fun subscribeNavigation() {
        compositeDisposable.add(
                viewModel.channel.ofNavigation()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            when(it) {
                                is FeedInputNavigation.NavigateToMyFeed -> {
                                    finish()
                                }
                            }
                        }
        )

    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showFeedInputImage, ::handleShowFeedInputImage)
        observe(viewModel.showInputInfo, ::handleShowInputInfo)
    }

    private fun handleShowInputInfo(looknFeel: FeedInputLooknFeel.ShowInputInfo) {
        address.text = looknFeel.address
        date.text    = looknFeel.date
    }

    private fun handleShowFeedInputImage(looknfeel: FeedInputLooknFeel.ShowFeedInputImage) {
        val feedInputImageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, looknfeel.imageUri)

        feed_input_image.setImageBitmap(feedInputImageBitmap)
    }

    private fun initializeListener() {
        save.setOnClickListener {
            viewModel.channel.accept(FeedInputViewAction.SaveFeed(title_input.text.toString(), description_input.text.toString()))
        }

        cancel.setOnClickListener {
            finish()
        }
    }

}