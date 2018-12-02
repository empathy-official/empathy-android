package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.repository.model.TourInfo


internal abstract class AbstractTourViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(tour: TourInfo)

}