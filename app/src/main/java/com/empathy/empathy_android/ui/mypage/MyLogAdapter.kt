package com.empathy.empathy_android.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.MyLog

internal class MyLogAdapter: RecyclerView.Adapter<MyLogViewHolder>() {

    private val myLogs = mutableListOf<MyLog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLogViewHolder
            = MyLogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_log, parent, false))

    override fun getItemCount(): Int = myLogs.size

    override fun onBindViewHolder(holder: MyLogViewHolder, position: Int) = holder.bind(myLogs[position])

    fun setMyLogs(myLogs: MutableList<MyLog>) {
        this.myLogs.clear()
        this.myLogs.addAll(myLogs)
        this.notifyDataSetChanged()
    }

}
