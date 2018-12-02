package com.empathy.empathy_android.ui.partnerinfo.tourfragment


import android.content.Intent
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
import com.empathy.empathy_android.ui.partnerinfo_detail.PartnerInfoDetailActivity
import com.empathy.empathy_android.utils.OnSwipeTouchListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
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

    private var listener: OnSwipeTouchListener.OnViewTransitionListener? = null

    private var swipeTouchListener: OnSwipeTouchListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tour_organization, container, false).apply {
            setOnTouchListener(OnSwipeTouchListener(context, listener))
        }

        swipeTouchListener = OnSwipeTouchListener(view.context, listener)

        view.setOnTouchListener(swipeTouchListener)
        view.setOnClickListener {
            if (swipeTouchListener!!.swipeDetected()){
                listener?.viewTransitioned()
            } else {

            }

        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
        initializeListener()

        viewModel.channel.accept(FragmentLifeCycle.OnViewCreated(arguments))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeLooknFeel()
        subscribeNavigation()

        viewModel.channel.accept(FragmentLifeCycle.OnActivityCreated(savedInstanceState))
    }

    fun setOnSwipeListener(listener: OnSwipeTouchListener.OnViewTransitionListener) {
        this.listener = listener
    }

    private fun subscribeNavigation() {
        viewModel.channel
                .ofNavigation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    when(it) {
                        is TourOrganizationNavigation.NavigateToTourDetail -> {
                            startActivity(Intent(context, PartnerInfoDetailActivity::class.java).apply {
                                putExtra(Constants.EXTRA_KEY_PARTNER_INFO_DETAIL_TYPE, PartnerInfoDetailActivity.TYPE_TOUR)
                                putExtra(Constants.EXTRA_KEY_TARGET_ID, it.targetId)
                                putExtra(Constants.EXTRA_KEY_CONTENT_TYPE, it.contentType)
                            })
                        }
                    }
                }
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
    }

    private fun initializeListener() {
        tourAdapter.setOnItemClickListener(object : TourViewHolder.OnItemClickListener {
            override fun onItemClicked(targetId: String, contentType: String) {
                viewModel.channel.accept(TourOrganizationViewAction.NavigateToDetailClicked(targetId, contentType))
            }
        })
    }

}
