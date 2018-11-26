package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.http.appchannel.LifecycleState
import com.empathy.empathy_android.repository.model.OthersLog
import com.empathy.empathy_android.ui.info.InfoActivity
import com.empathy.empathy_android.ui.mypage.MyFeedActivity
import com.empathy.empathy_android.ui.tmap.MapActivity
import kotlinx.android.synthetic.main.activity_feed.*

internal class FeedActivity : BaseActivity<FeedViewModel.ViewModel>() {

    private val logAdapter = FeedRecyclerAdapter()

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel.ViewModel> = FeedViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(LifecycleState.OnCreate(intent, savedInstanceState))

        initializeView()
        initializeListener()
    }

    private fun initializeView() {
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
            startActivity(Intent(this, MyFeedActivity::class.java))
        }

        info_container.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        t_map.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

}
