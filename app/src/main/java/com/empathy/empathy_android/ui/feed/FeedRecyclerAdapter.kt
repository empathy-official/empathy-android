package com.empathy.empathy_android.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.Feed

internal class FeedRecyclerAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(feedId: Int?)
    }

    private var listener: ItemClickListener? = null

    private val othersLogs = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
            FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_others_feed, parent, false)).apply {
                setOnItemClickListener(listener = object : FeedViewHolder.ItemClickListner {
                    override fun onItemClicked(feedId: Int?) {
                        listener?.onItemClicked(feedId)
                    }
                })
            }

    override fun getItemCount(): Int = othersLogs.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) = holder.bind(othersLogs[position])

    fun setFeeds(othersLogs: MutableList<Feed>) {
        this.othersLogs.clear()
        this.othersLogs.addAll(othersLogs)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

}
