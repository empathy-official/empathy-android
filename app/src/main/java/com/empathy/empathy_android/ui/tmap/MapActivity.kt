package com.empathy.empathy_android.ui.tmap

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.empathy.empathy_android.*
import com.empathy.empathy_android.extensions.toPx
import com.skt.Tmap.TMapCircle
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapGpsManager.NETWORK_PROVIDER
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_map.*


internal class MapActivity: BaseActivity<MapViewModel.ViewModel>(), TMapGpsManager.onLocationChangedCallback, View.OnClickListener {

    companion object {
        private var circleId = 0
        private const val START_LOCATION = 95
        private const val DEFAULT_RANGE = "300"
    }

    private val hideListener = object : SimpleAnimatorListener() {
        override fun onAnimationEnd(animation: Animator?) {
            range_0.visibility = View.INVISIBLE
            range_1.visibility = View.INVISIBLE
            range_2.visibility = View.INVISIBLE
        }
    }

    private val showListener = object : SimpleAnimatorListener() {
        override fun onAnimationStart(animation: Animator?) {
            range_0.visibility = View.VISIBLE
            range_1.visibility = View.VISIBLE
            range_2.visibility = View.VISIBLE
        }
    }

    private val tMapView by lazy {
        TMapView(this)
    }

    private val tmapGpsManager by lazy {
        TMapGpsManager(this)
    }

    private val range300 by lazy(LazyThreadSafetyMode.NONE) {
        ObjectAnimator.ofFloat(range_0 as TextView, "x", START_LOCATION.toPx, START_LOCATION.toPx + 16.toPx)
    }

    private val range500 by lazy(LazyThreadSafetyMode.NONE) {
        ObjectAnimator.ofFloat(range_1 as TextView, "x", START_LOCATION.toPx, START_LOCATION.toPx + 98.toPx)
    }

    private val range1000 by lazy(LazyThreadSafetyMode.NONE) {
        ObjectAnimator.ofFloat(range_2 as TextView, "x", START_LOCATION.toPx, START_LOCATION.toPx + 180.toPx)
    }

    private val rangeTextView by lazy {
        arrayOf<TextView>(range_0, range_1, range_2)
    }

    private var ranges: MutableList<String>? = null

    private var isExpanded = false

    private var latitude: Double  = 0.0
    private var longitude: Double = 0.0

    override fun getLayoutRes(): Int = R.layout.activity_map
    override fun getViewModel(): Class<MapViewModel.ViewModel> = MapViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val addresss = intent.getStringExtra(Constants.EXTRA_KEY_ADDRESS)

        current_location_title.text = addresss

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

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.range_selected -> {
                if(isExpanded) {
                    hideRanges()
                } else {
                    showRanges()
                }
            }
            R.id.range_0,
            R.id.range_1,
            R.id.range_2 -> {
                val range = findViewById<TextView>(view.id).text.toString()

                rearrangeRange(range)
                drawRangeCircle(latitude, longitude, range)
                hideRanges()
            }
        }
    }

    private fun showRanges() {
        with(AnimatorSet()) {
            interpolator = LinearInterpolator()

            addListener(showListener)

            setDuration(250)
                    .play(range300)
                    .with(range500)
                    .with(range1000)

            start()
        }

        filter_arrow.animate()
                .rotation(360F)
                .setDuration(200)
                .start()

        isExpanded = true
    }

    private fun hideRanges() {
        with(AnimatorSet()) {
            interpolator = ReverseInterpolator()

            addListener(hideListener)

            setDuration(250)
                    .play(range300)
                    .with(range500)
                    .with(range1000)

            start()
        }

        filter_arrow.animate()
                .rotation(-180F)
                .setDuration(200)
                .start()

        isExpanded = false
    }

    private fun rearrangeRange(range: String) {
        range_selected.text = range

        ranges = mutableListOf("100m", "300m", "500m", "1000m")
        ranges?.remove(range)

        for(i in 0 until ranges?.count()!!) {
            rangeTextView[i].text = ranges!![i]
        }
    }

    private fun initializeView() {
        showLocationPermission()
    }

    private fun showLocationPermission() {
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

    private fun drawRangeCircle(latitude: Double, longitude: Double, range: String = DEFAULT_RANGE) {
        tMapView.removeAllTMapCircle()

        val tmapCircle  = TMapCircle().apply {
            radius      = range.removeSuffix("m").toDouble()
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
        range_selected.setOnClickListener(this)
        range_0.setOnClickListener(this)
        range_1.setOnClickListener(this)
        range_2.setOnClickListener(this)
    }

}