package com.empathy.empathy_android.ui.mypage

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.repository.model.MyLog

internal class MyFeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    private var listener: OnItemClickListener? = null

    init {
        initializeListener()
    }

    fun bind(myLog: MyLog) {

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
