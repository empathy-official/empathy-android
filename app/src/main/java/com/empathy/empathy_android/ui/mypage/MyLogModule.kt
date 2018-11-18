package com.empathy.empathy_android.ui.mypage

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(MyLogModule.ProvideModule::class))
internal interface MyLogModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(MyLogViewModel.ViewModel::class)
    fun bindMyPageViewModel(myPageViewModel: MyLogViewModel.ViewModel): ViewModel
}