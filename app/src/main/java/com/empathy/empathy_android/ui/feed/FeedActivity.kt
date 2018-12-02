package com.empathy.empathy_android.ui.feed

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.ActivityLifecycleState
import com.empathy.empathy_android.ui.feeddetail.FeedDetailActivity
import com.empathy.empathy_android.ui.myfeed.MyFeedsActivity
import com.empathy.empathy_android.ui.partnerinfo.PartnerInfoActivity
import com.empathy.empathy_android.ui.tmap.MapActivity
import com.empathy.empathy_android.utils.OnSwipeTouchListener
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

internal class FeedActivity : BaseActivity<FeedViewModel>(), TMapGpsManager.onLocationChangedCallback {

    private val logAdapter = FeedRecyclerAdapter()

    @Inject lateinit var compositeDisposable: CompositeDisposable

    private val tmapView by lazy {
        TMapView(this)
    }

    private val tmapGpsManager by lazy {
        TMapGpsManager(this)
    }

    override fun getLayoutRes(): Int = R.layout.activity_feed
    override fun getViewModel(): Class<FeedViewModel> = FeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.channel.accept(ActivityLifecycleState.OnCreate(intent, savedInstanceState))

        subscribeLooknFeel()
        subscribeNavigation()

        initializeView()
        initializeListener()
    }

    override fun onLocationChange(location: Location?) {
        val latitude   = location?.latitude
        val longtitude = location?.longitude

        viewModel.channel.accept(FeedViewAction.OnLocationChange(latitude ?: Constants.DEFAULT_LATITUDE, longtitude ?: Constants.DEFAULT_LONGTITUDE))
    }

    override fun onDestroy() {
        super.onDestroy()

        tmapGpsManager.CloseGps()
        compositeDisposable.clear()
    }

    private fun subscribeNavigation() {
        compositeDisposable.add(
                viewModel.channel
                        .ofNavigation()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            when(it) {
                                is FeedNavigation.NavigateToFeed -> {
                                    startActivity(Intent(this, MyFeedsActivity::class.java).apply {
                                        putExtra(Constants.EXTRA_KEY_USER, it.user)
                                    })
                                }

                                is FeedNavigation.NavigateToPartnerInfo -> {
                                    startActivity(Intent(this, PartnerInfoActivity::class.java).apply {
                                        putExtra(Constants.EXTRA_KEY_USER, it.user)
                                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    })
                                }

                                is FeedNavigation.NavigateToTmap -> {
                                    startActivity(Intent(this, MapActivity::class.java).apply {
                                        putExtra(Constants.EXTRA_KEY_ADDRESS, it.address)
                                    })
                                }
                            }
                        }
        )
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.openGps, ::handleSetGps)
        observe(viewModel.closeGps, ::handleCloseGps)
        observe(viewModel.showLocation, ::handleShowLocation)
        observe(viewModel.showFeeds, ::handleShowFeeds)
        observe(viewModel.showFeedPlaceholder, ::handleShowFeedPlaceholder)
        observe(viewModel.showMyFeed, ::handleShowMyFeed)
        observe(viewModel.showSubContent, ::handleShowSubContent)
    }

    private fun handleCloseGps(closeGps: FeedLooknFeel.CloseGps) {
        tmapGpsManager.CloseGps()
    }

    private fun handleSetGps(openGps: FeedLooknFeel.SetGps) {
        tmapView.apply {
            setSKTMapApiKey("b5be9a2e-d454-4177-8912-1d2c1cbee391")
            setTrackingMode(true)
            setSightVisible(true)
        }

        tmapGpsManager.provider    = TMapGpsManager.NETWORK_PROVIDER
        tmapGpsManager.minTime     = 100
        tmapGpsManager.minDistance = 1F

        tmapGpsManager.OpenGps()
    }

    private fun handleShowLocation(looknFeel: FeedLooknFeel.ShowLocation) {
        toolbar.text = looknFeel.location
    }

    private fun handleShowFeedPlaceholder(looknFeel: FeedLooknFeel.ShowFeedPlaceHolder) {
        default_title.visibility = View.VISIBLE
        my_feed_container.loadImage(looknFeel.placeholderImage)
        default_content.text = looknFeel.content
        default_content.visibility = View.VISIBLE
    }

    private fun handleShowMyFeed(looknFeel: FeedLooknFeel.ShowMyFeed) {
        my_feed_title.visibility = View.VISIBLE
        my_feed_container.loadImage(looknFeel.myFeedImage)
        my_feed_content.text = looknFeel.content
        my_feed_content.visibility = View.VISIBLE
    }

    private fun handleShowSubContent(looknFeel: FeedLooknFeel.ShowSubContent) {
        sub_content.text = looknFeel.subContent
    }

    private fun handleShowFeeds(looknFeel: FeedLooknFeel.ShowFeeds) {
        logAdapter.setFeeds(looknFeel.feeds)
    }

    private fun initializeView() {
        with(log_recycler) {
            layoutManager = LinearLayoutManager(this@FeedActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter       = logAdapter
        }
    }

    private fun initializeListener() {
//        constLayout.setOnTouchListener (OnSwipeTouchListener(this@FeedActivity, "camera"))

        my_feed_container.setOnClickListener {
            viewModel.channel.accept(FeedViewAction.NavigateToMyFeedClicked)
        }

        info_container.setOnClickListener {
            viewModel.channel.accept(FeedViewAction.NavigateToPartnerInfoClicked)
        }

        filter_location.setOnClickListener {
            viewModel.channel.accept(FeedViewAction.NavigateToTmapClicked)
        }

        logAdapter.setOnItemClickListener(object : FeedRecyclerAdapter.ItemClickListener {
            override fun onItemClicked(feedId: Int?) {
                startActivity(Intent(this@FeedActivity, FeedDetailActivity::class.java).apply {
                    putExtra(Constants.EXTRA_KEY_FEED_DETAIL_TYPE, FeedDetailActivity.TYPE_OTHER_FEED)
                    putExtra(Constants.EXTRA_KEY_FEED_ID, feedId)
                })
            }
        })
    }

}
