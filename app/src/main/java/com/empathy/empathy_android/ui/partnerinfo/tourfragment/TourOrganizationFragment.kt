package com.empathy.empathy_android.ui.partnerinfo.tourfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseFragment
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.repository.model.Tour
import kotlinx.android.synthetic.main.fragment_tour_organization.*

internal class TourOrganizationFragment : BaseFragment() {

    companion object {
        const val KEY_BUNDLE_USER = "key_bundle_user"

        fun newInstance(user: LocalUser) = TourOrganizationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_BUNDLE_USER, user)
            }
        }
    }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel(TourOrganizationViewModel::class.java)
    }

    private val tourAdapter by lazy {
        TourAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_tour_organization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()

        viewModel.channel.accept(FragmentLifeCycle.OnViewCreated(arguments))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeLooknFeel()

        viewModel.channel.accept(FragmentLifeCycle.OnActivityCreated(savedInstanceState))
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showTourInfos, ::handleShowTourInfos)
    }

    private fun handleShowTourInfos(looknFeel: TourOrganizationLooknFeel.ShowTourInfos) {
        tourAdapter.setTours(looknFeel.tourInfos)
    }

    private fun initializeRecycler() {
        with(tour_recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = tourAdapter
        }

//        //dummy
//        val tours = mutableListOf<Tour>().apply {
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//            add(Tour("경복궁 체험행사", "왕십리 회랑로"))
//
//        }
//
//        tourAdapter.setTours(tours)
    }
}
