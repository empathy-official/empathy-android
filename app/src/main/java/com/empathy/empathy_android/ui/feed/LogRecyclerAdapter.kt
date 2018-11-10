package com.empathy.empathy_android.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.OthersLog

internal class LogRecyclerAdapter : RecyclerView.Adapter<LogViewHolder>() {

    private val othersLogs = mutableListOf<OthersLog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder
            = LogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_others_log, parent, false))

    override fun getItemCount(): Int = othersLogs.size

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) = holder.bind(othersLogs[position])

    fun setOthersLogs(othersLogs: MutableList<OthersLog>) {
        this.othersLogs.clear()
        this.othersLogs.addAll(othersLogs)
        this.notifyDataSetChanged()
    }

}
