package com.empathy.empathy_android.ui.myfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.MyFeed

internal class MyFeedAdapter: RecyclerView.Adapter<MyFeedViewHolder>() {

    private val myFeeds = mutableListOf<MyFeed>()

    private var listener: MyFeedViewHolder.OnItemClickListener? = null
    private var longListener: MyFeedViewHolder.OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedViewHolder
            = MyFeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_feed, parent, false))
            .apply {
                setOnItemClickListener(object : MyFeedViewHolder.OnItemClickListener {
                    override fun onItemClicked(position: Int) {
                        listener?.onItemClicked(position)
                    }
                })

                setOnItemLongClickListener(object : MyFeedViewHolder.OnItemLongClickListener {
                    override fun onItemLongClicked(targetId: Int?, position: Int) {
                        longListener?.onItemLongClicked(position, adapterPosition)
                    }
                })
            }

    override fun getItemCount(): Int = myFeeds.size

    override fun onBindViewHolder(holder: MyFeedViewHolder, position: Int) = holder.bind(myFeeds[position])

    fun setOnItemClickListener(listener: MyFeedViewHolder.OnItemClickListener) {
        this.listener = listener
    }

    fun setOnItemLongClickListener(listener: MyFeedViewHolder.OnItemLongClickListener) {
        this.longListener = listener
    }

    fun setMyFeeds(myLogs: MutableList<MyFeed>) {
        this.myFeeds.clear()
        this.myFeeds.addAll(myLogs)
        this.notifyDataSetChanged()
    }

    fun deleteMyFeed(deletePosition: Int) {
        this.myFeeds.removeAt(deletePosition)
        this.notifyDataSetChanged()
    }

}
