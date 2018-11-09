package com.empathy.empathy_android.ui.tmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.empathy.empathy_android.R

internal class RangeAdapter : BaseAdapter() {

    interface RangeItemClickListener {
        fun onRangeItemClicked(range: String)
    }

    private val ranges = listOf("100m", "300m", "500m", "1000m")

    private var listener: RangeItemClickListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            view       = LayoutInflater.from(parent?.context).inflate(R.layout.layout_t_map_search_range, parent, false)
            viewHolder = ViewHolder(view)

            view.tag = viewHolder
        } else {
            view       = convertView
            viewHolder = view.tag as ViewHolder
        }

        val range = ranges[position]

        with(viewHolder) {
            rangeTitle.text = range

            rangeContainer.setOnClickListener {
                listener?.onRangeItemClicked(range)
            }
        }

        return view
    }

    override fun getItem(position: Int): Any = ranges[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = ranges.count()

    fun setRangeItemClickListener(listener: RangeItemClickListener) {
        this.listener = listener
    }

    private class ViewHolder(convertView: View) {
        val rangeContainer = convertView.findViewById<LinearLayout>(R.id.range_container)
        val rangeTitle     = convertView.findViewById<TextView>(R.id.range)
    }

}
