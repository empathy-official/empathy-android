package com.empathy.empathy_android

import android.app.Activity
import android.app.Application
import com.empathy.empathy_android.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


internal class EmpathyApp: Application(), HasActivityInjector {

    companion object {
        lateinit var instance: EmpathyApp
            private set
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        instance = this

        initializeDagger()
        initializeLeakcanary()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initializeDagger() {
        DaggerApplicationComponent
                .builder()
                .create(this)
                .inject(this)
    }

    private fun initializeLeakcanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
    }

}