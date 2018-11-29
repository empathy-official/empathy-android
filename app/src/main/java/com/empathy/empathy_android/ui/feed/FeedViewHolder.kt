package com.empathy.empathy_android.ui.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.repository.model.Feed
import kotlinx.android.synthetic.main.item_others_feed.view.*

internal class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    interface ItemClickListner {
        fun onItemClicked(feedId: Int?)
    }

    private var listener: ItemClickListner? = null

    private var feed: Feed? = null

    init {
        initializeListener()
    }

    fun bind(feed: Feed) {
        this.feed = feed

        with(itemView) {
            feed_image.loadImage(feed.imageUrl)
            profile_image.loadImage(feed.ownerProfileUrl)
            username.text = feed.ownerName
        }
    }

    fun setOnItemClickListener(listener: ItemClickListner) {
        this.listener = listener
    }

    private fun initializeListener() {
        itemView.setOnClickListener {
            listener?.onItemClicked(feed?.journeyId)
        }
    }

}
