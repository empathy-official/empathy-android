package com.empathy.empathy_android.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.MyLog

internal class MyFeedAdapter: RecyclerView.Adapter<MyFeedViewHolder>() {

    private val myLogs = mutableListOf<MyLog>()

    private var listener: MyFeedViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedViewHolder
            = MyFeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_feed, parent, false))
            .apply {
                setOnItemClickListener(object : MyFeedViewHolder.OnItemClickListener {
                    override fun onItemClicked(position: Int) {
                        listener?.onItemClicked(position)
                    }
                })
            }

    override fun getItemCount(): Int = myLogs.size

    override fun onBindViewHolder(holder: MyFeedViewHolder, position: Int) = holder.bind(myLogs[position])

    fun setOnItemClickListener(listener: MyFeedViewHolder.OnItemClickListener) {
        this.listener = listener
    }

    fun setMyLogs(myLogs: MutableList<MyLog>) {
        this.myLogs.clear()
        this.myLogs.addAll(myLogs)
        this.notifyDataSetChanged()
    }

}
