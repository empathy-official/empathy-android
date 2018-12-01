package com.empathy.empathy_android.ui.myfeed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.repository.model.MyFeed
import kotlinx.android.synthetic.main.item_my_feed.view.*

internal class MyFeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(targetId: Int?, adapterPosition: Int)
    }

    private var myFeed: MyFeed? = null

    private var listener: OnItemClickListener? = null
    private var longListener: OnItemLongClickListener? = null

    init {
        initializeListener()
    }

    fun bind(myFeed: MyFeed) {
        this.myFeed = myFeed

        with(itemView) {
            my_log_image.loadImage(myFeed.imageUrl)

            date.text  = myFeed.creationTime
            title.text = myFeed.title
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.longListener = listener
    }

    private fun initializeListener() {
        itemView.setOnClickListener {
            listener?.onItemClicked(adapterPosition)
        }

        itemView.setOnLongClickListener {
            longListener?.onItemLongClicked(myFeed?.journeyId, adapterPosition)

            return@setOnLongClickListener true
        }
    }

}
