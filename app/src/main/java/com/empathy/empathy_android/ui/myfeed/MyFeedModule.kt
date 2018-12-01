package com.empathy.empathy_android.ui.myfeed

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [MyFeedModule.ProvideModule::class])
internal interface MyFeedModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    fun bindsMyFeedChannel(channel: MyFeedChannel): MyFeedChannelApi

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(MyFeedViewModel::class)
    fun bindMyPageViewModel(viewModel: MyFeedViewModel): ViewModel
}