package com.empathy.empathy_android.ui.partnerinfo.tourfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.empathy.empathy_android.R
import com.empathy.empathy_android.repository.model.Tour
import kotlinx.android.synthetic.main.fragment_tour_organization.*

internal class TourOrganizationFragment : Fragment() {


    companion object {
        fun newInstance() = TourOrganizationFragment().apply {
            arguments = Bundle().apply {
//                putInt()
            }
        }
    }

    private val tourAdapter by lazy {
        TourAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.getInt("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_tour_organization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
    }

    private fun initializeRecycler() {
        with(tour_recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = tourAdapter
        }

        //dummy
        val tours = mutableListOf<Tour>().apply {
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
            add(Tour("경복궁 체험행사", "왕십리 회랑로"))

        }

        tourAdapter.setTours(tours)
    }
}
