package com.empathy.empathy_android.di

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.empathy.empathy_android.BaseViewModelFactory
import com.empathy.empathy_android.EmpathyApp
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule.ProvideModule::class])
internal abstract class ApplicationModule {

    @Module
    class ProvideModule {

        @Singleton
        @Provides
        fun provideContext(): Context = EmpathyApp.instance
    }

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

}
