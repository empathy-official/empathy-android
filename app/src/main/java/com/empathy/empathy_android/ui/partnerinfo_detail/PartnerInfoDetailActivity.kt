package com.empathy.empathy_android.ui.partnerinfo_detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_partner_info_detail.*
import javax.inject.Inject


internal class PartnerInfoDetailActivity: BaseActivity<PartnerInfoDetailViewModel>() {

    companion object {
        const val TYPE_PARTNER = "partner"
        const val TYPE_TOUR    = "tour"
    }

    @Inject lateinit var appChannel: AppChannelApi

    private val tMapView by lazy {
        TMapView(this)
    }

    private val requestManager by lazy {
        Glide.with(this)
    }

//    private val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)
    private val disposables = CompositeDisposable()

    override fun getLayoutRes(): Int = R.layout.activity_partner_info_detail
    override fun getViewModel(): Class<PartnerInfoDetailViewModel> = PartnerInfoDetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTmap()

        intent.getStringExtra(Constants.EXTRA_KEY_PARTNER_INFO_DETAIL_TYPE).let {
            if(it == TYPE_PARTNER) {
                val partnerId = intent.getStringExtra(Constants.EXTRA_KEY_PARTNER_ID) ?: ""

                fetchPartnerDetailInfo(partnerId)
            } else if(it == TYPE_TOUR) {
                val targetId = intent.getStringExtra(Constants.EXTRA_KEY_TARGET_ID) ?: ""
                val contentType = intent.getStringExtra(Constants.EXTRA_KEY_CONTENT_TYPE) ?: ""

                fetchTourDetailInfo(contentType, targetId)
            }
        }

        subscribeRemote()
    }

    private fun fetchTourDetailInfo(contentType: String, targetId: String) {
        appChannel.accept(AppData.RequestTo.Remote.FetchTourInfoDetail(contentType, targetId))
    }

    private fun fetchPartnerDetailInfo(partnerId: String) {
        appChannel.accept(AppData.RequestTo.Remote.FetchPartnerInfoDetail(partnerId))
    }

    private fun subscribeRemote() {
        val onRemote = appChannel.ofData().ofType(AppData.RespondTo.Remote::class.java)

        disposables.add(
                onRemote.observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            when(it) {
                                is AppData.RespondTo.Remote.TourInfoDetailFetched -> {
                                    val tourDetail = it.tourDetail

                                    requestManager.load(tourDetail.imageURL).into(info_detail_image)

                                    parnter_title.text = tourDetail.title
                                    content.text = tourDetail.overviewText
                                    date.text = tourDetail.businessHours
                                    time_or_dayoff.text = tourDetail.dayOff
                                    price_info.text = "정보 안나와"

                                    credit_container.visibility = View.VISIBLE
                                    creditcard_available.text = tourDetail.creditCard

                                    pet_container.visibility = View.VISIBLE
                                    pet_available.text = tourDetail.withPet

                                    address.text = tourDetail.locationStr

                                    val latitude = tourDetail.mapx
                                    val longtitude = tourDetail.mapy
                                    val pinBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_pin)

                                    val marker = TMapMarkerItem().apply {
                                        tMapPoint = TMapPoint(latitude.toDouble(), longtitude.toDouble())
                                        icon = pinBitmap
                                    }

                                    tMapView.addMarkerItem("location", marker)
                                }

                                is AppData.RespondTo.Remote.PartnerInfoDetailFetched -> {
                                    val partnerDetail = it.tourInfoDetail

                                    requestManager.load(partnerDetail.imageURL).into(info_detail_image)

                                    parnter_title.text = partnerDetail.title
                                    content.text = partnerDetail.overview
                                    date.text = partnerDetail.duration
                                    time_or_dayoff.text = partnerDetail.playTime
                                    price_info.text = partnerDetail.priceInfo
                                    address.text = partnerDetail.locationStr

                                    val latitude = partnerDetail.mapx
                                    val longtitude = partnerDetail.mapy
                                    val pinBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_pin)

                                    val marker = TMapMarkerItem().apply {
                                        tMapPoint = TMapPoint(latitude.toDouble(), longtitude.toDouble())
                                        icon = pinBitmap
                                    }

                                    tMapView.addMarkerItem("location", marker)
                                }
                            }
                        }
        )
    }

    private fun setTmap() {
        with(t_map_container) {
            addView(tMapView.apply {
                zoomLevel = 14

                setIconVisibility(true)
            })
        }
    }
}