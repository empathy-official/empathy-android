package com.empathy.empathy_android.ui.feeddetail

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [FeedDetailModule.ProvideModule::class])
internal interface FeedDetailModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(FeedDetailViewModel::class)
    fun bindFeedDetailViewModel(feedDetailViewModel: FeedDetailViewModel): ViewModel

}