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

    private val popupWindow = PopupWindow()

    private val spinnerAdapter = RangeAdapter()

    private val tMapView by lazy {
        TMapView(this)
    }

    private val tmapGpsManager by lazy {
        TMapGpsManager(this)
    }

    override fun getLayoutRes(): Int = R.layout.activity_map
    override fun getViewModel(): Class<MapViewModel.ViewModel> = MapViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeView()
        initializeListener()
    }

    override fun onLocationChange(location: Location?) {
        tMapView.setLocationPoint(location?.longitude!!, location.latitude)
        
        drawRangeCircle(location.longitude, location.latitude)
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

        tmapGpsManager.provider = NETWORK_PROVIDER
        tmapGpsManager.minTime = 1000
        tmapGpsManager.minDistance = 5F
        tmapGpsManager.OpenGps()
    }

    private fun drawRangeCircle(longitude: Double, latitude: Double) {
        val tmapCircle = TMapCircle().apply {
            radius = 1000.0
            lineColor = Color.BLUE
            areaColor = Color.GRAY
            areaAlpha = 100
            circleWidth = 3.toFloat()
            centerPoint = TMapPoint(latitude, longitude)
        }

        tMapView.addTMapCircle(
                String.format("circle%d", circleId++),
                tmapCircle
        )
    }

    private fun initializeListener() {
        search_range.setOnClickListener {

        }

        spinnerAdapter.setRangeItemClickListener(listener = object : RangeAdapter.RangeItemClickListener {
            override fun onRangeItemClicked(range: String) {

            }
        })
    }

}