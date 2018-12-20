package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.TourInfo

internal class TourAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NO_IMAGE   = 0
        private const val TYPE_WITH_IMAGE = 1
    }

    private val tours = mutableListOf<TourInfo>()

    private var listener: TourViewHolder.OnItemClickListener? = null

    override fun getItemViewType(position: Int): Int {
        val image = tours[position].imageURL

        if(image == "") {
            return TYPE_NO_IMAGE
        }

        return TYPE_WITH_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        if(viewType == 0) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour_no_image, parent, false)

            return TourNoImageViewHolder(view)
        }

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)

        return TourViewHolder(view).apply {
            setOnItemClickListener(listener)
        }
    }

    override fun getItemCount(): Int = tours.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = when(holder.itemViewType) {
            0 -> { holder as TourNoImageViewHolder }
            1 -> { holder as TourViewHolder }
            else -> { holder as TourViewHolder  }
        }

        viewHolder.bind(tours[position])
    }

    fun setTours(tours: MutableList<TourInfo>) {
        this.tours.clear()
        this.tours.addAll(tours)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: TourViewHolder.OnItemClickListener) {
        this.listener = listener
    }

}
