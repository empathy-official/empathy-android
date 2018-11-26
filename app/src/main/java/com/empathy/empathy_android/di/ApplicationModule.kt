package com.empathy.empathy_android.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.empathy.empathy_android.BaseViewModelFactory
import com.empathy.empathy_android.EmpathyApp
import com.empathy.empathy_android.http.appchannel.AppChannel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.repository.EmpathyRepository
import com.empathy.empathy_android.repository.EmpathyRepositoryApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule.ProvideModule::class])
internal interface ApplicationModule {

    @Module
    class ProvideModule {

        @Singleton
        @Provides
        fun provideContext(): Context = EmpathyApp.instance
    }

    @Binds
    @Singleton
    fun bindsAppChannel(appChannel: AppChannel): AppChannelApi

    @Binds
    @Singleton
    fun bindsEmpathyRepository(repository: EmpathyRepository): EmpathyRepositoryApi

    @Binds
    fun bindsViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

}
