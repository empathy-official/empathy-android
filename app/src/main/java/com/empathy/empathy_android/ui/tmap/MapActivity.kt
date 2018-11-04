package com.empathy.empathy_android.ui.tmap

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.login.MapViewModel
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.map_activity.*


internal class MapActivity: BaseActivity<MapViewModel.ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)

        tmap_container.addView(
                TMapView(this).apply {
                    setSKTMapApiKey("b5be9a2e-d454-4177-8912-1d2c1cbee391")
                }
        )
    }
}