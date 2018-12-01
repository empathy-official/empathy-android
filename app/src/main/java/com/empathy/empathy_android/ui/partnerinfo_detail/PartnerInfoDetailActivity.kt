package com.empathy.empathy_android.ui.partnerinfo_detail

import android.graphics.BitmapFactory
import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.Constants
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.http.appchannel.AppData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_partner_info_detail.*
import javax.inject.Inject


internal class PartnerInfoDetailActivity: BaseActivity<PartnerInfoDetailViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_partner_info_detail
    override fun getViewModel(): Class<PartnerInfoDetailViewModel> = PartnerInfoDetailViewModel::class.java

    @Inject lateinit var appChannel: AppChannelApi

    private val tMapView by lazy {
        TMapView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTmap()

        val partnerId = intent.getStringExtra(Constants.EXTRA_KEY_PARTNER_ID)

        appChannel.accept(AppData.RequestTo.Remote.FetchPartnerInfoDetail(partnerId))

        appChannel.ofData().ofType(AppData.RespondTo.Remote.PartnerInfoDetailFetched::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {

                    val tourInfoDetail = it.tourInfoDetail

                    info_detail_image.loadImage(tourInfoDetail.imageURL)
                    parnter_title.text = tourInfoDetail.title
                    content.text = tourInfoDetail.overview
                    date.text = tourInfoDetail.duration
                    time.text = tourInfoDetail.playTime
                    price_info.text = tourInfoDetail.priceInfo
                    address.text = tourInfoDetail.locationStr

                    val latitude = tourInfoDetail.mapx
                    val longtitude = tourInfoDetail.mapy
                    val pinBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_pin)

                    val marker = TMapMarkerItem().apply {
                        tMapPoint = TMapPoint(latitude.toDouble(), longtitude.toDouble())
                        icon = pinBitmap
                    }

                    tMapView.addMarkerItem("location", marker)
        }
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