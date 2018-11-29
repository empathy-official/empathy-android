package com.empathy.empathy_android.ui.mypage

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.repository.model.MyFeed
import kotlinx.android.synthetic.main.item_my_feed.view.*

internal class MyFeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    private var listener: OnItemClickListener? = null

    init {
        initializeListener()
    }

    fun bind(myFeed: MyFeed) {
        with(itemView) {
            my_log_image.loadImage(myFeed.imageUrl)

            date.text  = myFeed.creationTime
            title.text = myFeed.title
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private fun initializeListener() {
        itemView.setOnClickListener {
            listener?.onItemClicked(adapterPosition)
        }
    }

}
