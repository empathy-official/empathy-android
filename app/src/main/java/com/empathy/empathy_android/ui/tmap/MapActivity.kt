package com.empathy.empathy_android.ui.tmap

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ListView
import android.widget.PopupWindow
import androidx.core.content.res.ResourcesCompat
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.login.MapViewModel
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_map.*
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import android.util.Log
import android.view.Gravity
import com.empathy.empathy_android.extensions.toPx
import com.skt.Tmap.TMapCircle
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapGpsManager.GPS_PROVIDER
import com.skt.Tmap.TMapGpsManager.NETWORK_PROVIDER
import com.skt.Tmap.TMapPoint
import com.tbruyelle.rxpermissions2.RxPermissions


internal class MapActivity: BaseActivity<MapViewModel.ViewModel>(), TMapGpsManager.onLocationChangedCallback {

    companion object {
        private var circleId = 0
    }

    private val tMapView by lazy {
        TMapView(this)
    }

    private val tmapGpsManager by lazy {
        TMapGpsManager(this)
    }

    private val popupWindow = PopupWindow()

    private val spinnerAdapter = RangeAdapter()

    private var latitude: Double  = 0.0
    private var longitude: Double = 0.0

    override fun getLayoutRes(): Int = R.layout.activity_map
    override fun getViewModel(): Class<MapViewModel.ViewModel> = MapViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeView()
        initializeListener()
    }

    override fun onLocationChange(location: Location?) {
        tMapView.setLocationPoint(location?.longitude!!, location.latitude)

        this.latitude = location.latitude
        this.longitude = location.longitude

        drawRangeCircle(location.latitude, location.longitude)
    }

    override fun onDestroy() {
        super.onDestroy()

        tmapGpsManager.CloseGps()
    }

    private fun initializeView() {
        setSupportActionBar(toolbar)
        setRangePopupWindow()
        setRxPermission()
    }

    private fun setRangePopupWindow() {
        val rangeListView = ListView(this).apply {
            adapter       = spinnerAdapter
            dividerHeight = 0
        }

        popupWindow.apply {
            contentView        = rangeListView
            height             = WindowManager.LayoutParams.WRAP_CONTENT
            width              = WindowManager.LayoutParams.WRAP_CONTENT
            isFocusable        = true
            isOutsideTouchable = true
            animationStyle     = android.R.style.Animation_Dialog

            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dismiss()
        }
    }

    private fun setRxPermission() {
        RxPermissions(this).run {
            request(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if(granted) {
                            setTmap()

                            return@subscribe
                        } else {
                            return@subscribe
                        }
                    }
        }
    }

    private fun setTmap() {
        with(t_map_container) {
            addView(tMapView.apply {
                zoomLevel = 14

                setSKTMapApiKey("b5be9a2e-d454-4177-8912-1d2c1cbee391")
                setIconVisibility(true)
                setTrackingMode(true)
                setSightVisible(true)
            })
        }

        tmapGpsManager.provider    = NETWORK_PROVIDER
        tmapGpsManager.minTime     = 1000
        tmapGpsManager.minDistance = 5F
        tmapGpsManager.OpenGps()
    }

    private fun drawRangeCircle(latitude: Double, longitude: Double, range: String = "1000") {
        tMapView.removeAllTMapCircle()

        val tmapCircle  = TMapCircle().apply {
            radius      = range.toDouble()
            lineColor   = Color.BLUE
            areaColor   = Color.GRAY
            areaAlpha   = 100
            circleWidth = 3.toFloat()
            centerPoint = TMapPoint(latitude, longitude)
        }

        tMapView.addTMapCircle(
                String.format("circle%d", circleId++),
                tmapCircle
        )
    }

    private fun initializeListener() {
        search_range.setOnClickListener { view ->
            if(popupWindow.isShowing) {
                popupWindow.dismiss()
            } else {
                popupWindow.showAsDropDown(view)
            }
        }

        spinnerAdapter.setRangeItemClickListener(listener = object : RangeAdapter.RangeItemClickListener {
            override fun onRangeItemClicked(range: String) {
                range_title.text = String.format("%sm", range)

                drawRangeCircle(latitude, longitude, range)

                popupWindow.dismiss()
            }
        })
    }

}