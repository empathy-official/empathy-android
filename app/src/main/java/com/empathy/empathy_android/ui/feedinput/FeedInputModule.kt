package com.empathy.empathy_android.ui.feedinput

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.qualifier.FeedInput
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = arrayOf(FeedInputModule.ProvideModule::class))
internal interface FeedInputModule {

    @Module
    class ProvideModule {
        @Provides
        @ActivityScope
        @FeedInput
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    @ActivityScope
    fun bindsFeedInputChannel(channel: FeedInputChannel): FeedInputChanneApi

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(FeedInputViewModel.ViewModel::class)
    fun bindsFeedInputViewModel(viewModel: FeedInputViewModel.ViewModel): ViewModel

}