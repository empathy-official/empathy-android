package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.TourInfo

internal class TourAdapter : RecyclerView.Adapter<TourViewHolder>() {

    private val tours = mutableListOf<TourInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)

        return TourViewHolder(view)
    }

    override fun getItemCount(): Int = tours.size

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) = holder.bind(tours[position])

    fun setTours(tours: MutableList<TourInfo>) {
        this.tours.clear()
        this.tours.addAll(tours)
        this.notifyDataSetChanged()
    }

}
