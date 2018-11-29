package com.empathy.empathy_android.ui.mypage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.ui.feeddetail.FeedDetailActivity
import com.empathy.empathy_android.ui.feedinput.FeedInputActivity
import kotlinx.android.synthetic.main.activity_my_feed.*


internal class MyFeedActivity: BaseActivity<MyFeedViewModel>() {

    companion object {
        private const val REQUEST_CODE_ALBUM = 1000

        const val EXTRA_KEY_USER_ID = "extra_key_user_id"

        const val EXTRA_KEY_FEED_IMAGE_URI = "extra_key_feed_image_uri"
    }

    private val adapter by lazy {
        MyFeedAdapter()
    }

    override fun getLayoutRes(): Int = R.layout.activity_my_feed
    override fun getViewModel(): Class<MyFeedViewModel> = MyFeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeRecycler()
        initializeListener()

        subscribeLooknFeel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ALBUM) {
            data?.data?.let {
                startActivity(Intent(this, FeedInputActivity::class.java).apply {
                    putExtra(EXTRA_KEY_FEED_IMAGE_URI, it.toString())
                })
            }
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
            layoutManager = LinearLayoutManager(this@MyFeedActivity)
            adapter = this@MyFeedActivity.adapter
        }
    }

    private fun initializeListener() {
        change_view_way.setOnClickListener {

        }

        add_log.setOnClickListener {
            startActivityForResult(Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), REQUEST_CODE_ALBUM)
        }

        adapter.setOnItemClickListener(object : MyFeedViewHolder.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                startActivity(Intent(this@MyFeedActivity, FeedDetailActivity::class.java).apply {
                    putExtra("position", position)
                })
            }
        })
    }

}