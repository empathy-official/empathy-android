package com.empathy.empathy_android.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.empathy.empathy_android.BaseViewModelFactory
import com.empathy.empathy_android.EmpathyApp
import com.empathy.empathy_android.di.qualifier.App
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.http.appchannel.AppChannel
import com.empathy.empathy_android.http.appchannel.AppChannelApi
import com.empathy.empathy_android.repository.EmpathyRepository
import com.empathy.empathy_android.repository.EmpathyRepositoryApi
import com.empathy.empathy_android.ui.login.LocationFilter
import com.empathy.empathy_android.ui.login.LocationFilterApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module(includes = [ApplicationModule.ProvideModule::class])
internal interface ApplicationModule {

    @Module
    class ProvideModule {

        @Singleton
        @Provides
        @App
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

        @Singleton
        @Provides
        fun provideContext(): Context = EmpathyApp.instance
    }

    @Binds
    @Singleton
    @LocFilter
    fun bindsLocationFilter(locationFilter: LocationFilter): LocationFilterApi

    @Binds
    @Singleton
    fun bindsAppChannel(appChannel: AppChannel): AppChannelApi

    @Binds
    @Singleton
    fun bindsEmpathyRepository(repository: EmpathyRepository): EmpathyRepositoryApi

    @Binds
    fun bindsViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

}
