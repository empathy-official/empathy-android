package com.empathy.empathy_android.ui.feed

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
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
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    @ActivityScope
    fun bindsFeedChannel(channel: FeedChannel): FeedChannelApi

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindsMainViewModel(mainViewModel: FeedViewModel): ViewModel

}
