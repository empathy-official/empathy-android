package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.OthersLog
import com.empathy.empathy_android.ui.info.InfoActivity
import com.empathy.empathy_android.ui.mypage.MyPageActivity
import com.empathy.empathy_android.ui.tmap.MapActivity
import kotlinx.android.synthetic.main.activity_feed.*

internal class FeedActivity : BaseActivity<FeedViewModel.ViewModel>() {

    private val logAdapter = LogRecyclerAdapter()

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel.ViewModel> = FeedViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeView()
        initializeListener()

    }

    private fun initializeView() {
        toolbar.title = "서울특별시 왕십로"

        setSupportActionBar(toolbar)

        with(log_recycler) {
            layoutManager = LinearLayoutManager(this@FeedActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = logAdapter
        }

        //dummy
        val othersLogs = mutableListOf<OthersLog>()
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))
        othersLogs.add(OthersLog("병탁", "시끄러운 경복궁에서", ""))

        logAdapter.setOthersLogs(othersLogs)
    }

    private fun initializeListener() {
        my_log_container.setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
        }

        info_container.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }
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
