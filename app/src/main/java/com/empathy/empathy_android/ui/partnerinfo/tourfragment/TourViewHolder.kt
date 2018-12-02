package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import android.view.View
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.repository.model.TourInfo
import kotlinx.android.synthetic.main.item_tour.view.*

internal class TourViewHolder(itemView: View): AbstractTourViewHolder(itemView) {

    interface OnItemClickListener {
        fun onItemClicked(targetId: String, contentType: String)
    }

    private var listener: OnItemClickListener? = null
    private var tour: TourInfo? = null

    init {
        initializeListener()
    }

    override fun bind(tour: TourInfo) {
        this.tour = tour

        with(itemView) {
            title.text   = tour.title
            address.text = tour.addr

            tour_image.loadImage(tour.imageURL)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    private fun initializeListener() {
        itemView.setOnClickListener {
            tour?.targetId?.let {
                listener?.onItemClicked(it, tour?.contentType!!)
            }
        }
    }

}
