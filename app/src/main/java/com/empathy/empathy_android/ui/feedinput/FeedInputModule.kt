package com.empathy.empathy_android.ui.feedinput

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = arrayOf(FeedInputModule.ProvideModule::class))
internal interface FeedInputModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(FeedInputViewModel.ViewModel::class)
    fun bindFeedInputViewModel(viewModel: FeedInputViewModel.ViewModel): ViewModel

}