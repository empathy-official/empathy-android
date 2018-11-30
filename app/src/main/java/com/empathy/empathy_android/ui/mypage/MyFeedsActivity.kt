package com.empathy.empathy_android.ui.mypage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.ui.feeddetail.FeedDetailActivity
import com.empathy.empathy_android.ui.feedinput.FeedInputActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_my_feed.*


internal class MyFeedsActivity: BaseActivity<MyFeedViewModel>() {

    companion object {
        const val REQUEST_CODE_ALBUM = 1000
    }

    private val selectedImageUri: Uri? = null

    private val adapter by lazy {
        MyFeedAdapter()
    }

    override fun getLayoutRes(): Int = R.layout.activity_my_feed
    override fun getViewModel(): Class<MyFeedViewModel> = MyFeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(LifecycleState.OnCreate(intent, savedInstanceState))

        initializeRecycler()
        initializeListener()

        subscribeNavigation()
        subscribeLooknFeel()
    }

    private fun subscribeNavigation() {
        viewModel.channel
                .ofNavigation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    when(it) {
                        is MyFeedNavigation.NavigateToFeedInput -> {
                            startActivity(Intent(this, FeedInputActivity::class.java).apply {
                                putExtra(Constants.EXTRA_KEY_FEED_IMAGE_URI, it.imageUri)
                                putExtra(Constants.EXTRA_KEY_USER, it.user)
                            })
                        }
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ALBUM) {
            viewModel.channel.accept(LifecycleState.OnActivityResult(requestCode, resultCode, data))
        }
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showMyFeeds, ::handleShowMyFeeds)
        observe(viewModel.showEmptyFeeds, ::handleShowEmptyFeeds)
    }

    private fun handleShowMyFeeds(looknFeel: MyFeedLooknFeel.ShowMyFeeds) {
        adapter.setMyFeeds(looknFeel.myFeeds)
    }

    private fun handleShowEmptyFeeds(looknFeel: MyFeedLooknFeel.ShowEmptyFeeds) {
        myfeeds_empty_container.visibility = View.VISIBLE
    }

    private fun initializeRecycler() {
        with(my_log_recycler) {
            layoutManager = LinearLayoutManager(this@MyFeedsActivity)
            adapter = this@MyFeedsActivity.adapter
        }
    }

    private fun initializeListener() {
        change_view_way.setOnClickListener {

        }

        add_feed.setOnClickListener {
            startActivityForResult(Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), REQUEST_CODE_ALBUM)
        }

        adapter.setOnItemClickListener(object : MyFeedViewHolder.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                startActivity(Intent(this@MyFeedsActivity, FeedDetailActivity::class.java).apply {
                    putExtra("position", position)
                })
            }
        })
    }

}