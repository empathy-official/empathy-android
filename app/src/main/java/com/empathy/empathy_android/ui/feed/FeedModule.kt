package com.empathy.empathy_android.ui.feed

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.di.qualifier.Main
import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.ui.login.LocationFilter
import com.empathy.empathy_android.ui.login.LocationFilterApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [FeedModule.ProvideModule::class])
internal interface FeedModule {

    @Module
    class ProvideModule {
        @Provides
        @ActivityScope
        @Main
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    @ActivityScope
    @LocFilter
    fun bindsLocationFilter(locationFilter: LocationFilter): LocationFilterApi

    @Binds
    @ActivityScope
    fun bindsFeedChannel(channel: FeedChannel): FeedChannelApi

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(FeedViewModel.ViewModel::class)
    fun bindsMainViewModel(mainViewModel: FeedViewModel.ViewModel): ViewModel

}
