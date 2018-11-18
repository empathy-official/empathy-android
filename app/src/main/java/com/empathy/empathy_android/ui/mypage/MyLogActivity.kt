package com.empathy.empathy_android.ui.mypage

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.MyLog
import kotlinx.android.synthetic.main.activity_my_log.*


internal class MyLogActivity: BaseActivity<MyLogViewModel.ViewModel>() {

    private val adapter by lazy {
        MyLogAdapter()
    }

    override fun getLayoutRes(): Int = R.layout.activity_my_log
    override fun getViewModel(): Class<MyLogViewModel.ViewModel> = MyLogViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeRecycler()
        initializeListener()
    }

    private fun initializeRecycler() {
        with(my_log_recycler) {
            layoutManager = LinearLayoutManager(this@MyLogActivity)
            adapter = this@MyLogActivity.adapter
        }

        //dummy
        val dummys = mutableListOf<MyLog>()

        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))
        dummys.add(MyLog("11.03 2018", "", "왕십리 시장 탐험을 다녀오다"))

        adapter.setMyLogs(dummys)
    }

    private fun initializeListener() {
        change_view_way.setOnClickListener {

        }

        add_log.setOnClickListener {

        }
    }

}