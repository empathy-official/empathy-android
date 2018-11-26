package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.tmap.MapActivity
import kotlinx.android.synthetic.main.activity_feed.*

internal class FeedActivity : BaseActivity<FeedViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel.ViewModel> = FeedViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeListener()

    }

    private fun initializeViews() {
        toolbar.title = "서울특별시 왕십로"

        setSupportActionBar(toolbar)
    }

    private fun initializeListener() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feed, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.t_map -> startActivity(Intent(this, MapActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
