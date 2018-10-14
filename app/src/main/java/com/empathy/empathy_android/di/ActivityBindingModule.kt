package com.empathy.empathy_android.di

import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.ui.MainActivity
import com.empathy.empathy_android.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

}
