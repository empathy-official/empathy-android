package com.empathy.empathy_android.ui.login

import android.Manifest
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.feed.FeedActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


internal class LoginActivity: BaseActivity<LoginViewModel.ViewModel>(), TMapGpsManager.onLocationChangedCallback {

    private var isGranted = false

    private val callbackManager = CallbackManager.Factory.create()

    private val compositeDisposable = CompositeDisposable()

    private val tmapView by lazy {
        TMapView(this)
    }

    private val tmapGpsManager by lazy {
        TMapGpsManager(this)
    }

    override fun getLayoutRes(): Int = R.layout.activity_login
    override fun getViewModel(): Class<LoginViewModel.ViewModel> = LoginViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLocationPermission()

        initializeFacebookPermission()
        initializeListener()
    }

    override fun onLocationChange(location: Location?) {
        val latitude   = location?.latitude
        val longtitude = location?.longitude

        tmapView.setLocationPoint(latitude!!, longtitude!!)

        viewModel.channel.accept(LoginViewAction.OnLocationChange(latitude, longtitude))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
        tmapGpsManager.CloseGps()
    }

    private fun showLocationPermission() {
        compositeDisposable.add(RxPermissions(this).run {
            request(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if(granted) {
                            isGranted = true

                            setGps()

                            return@subscribe
                        } else {
                            longToast("위치 정보에 동의를 하셔야만 로그인을 하실 수 있습니다.")

                            isGranted = false

                            return@subscribe
                        }
                    }
        })
    }

    private fun setGps() {
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

    private fun initializeFacebookPermission() {
        facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"))
    }

    private fun initializeListener() {
        facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                navigateToFeed()
            }

            override fun onCancel() {
                Log.d("result", "onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("LoginErr", error.toString())
            }
        })

        facebook_login_view.setOnClickListener {
            if (isGranted) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    navigateToFeed()

                    return@setOnClickListener
                }

                facebook_login.performClick()
            } else {
                showLocationPermission()
            }

        }
    }

    private fun navigateToFeed() {
        startActivity(Intent(this@LoginActivity, FeedActivity::class.java).apply {
//            putExtra(Constants.EXTRA_KEY_LOCATION_FILTER)
        })

        finish()
    }

}