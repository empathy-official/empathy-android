package com.empathy.empathy_android.di

import android.arch.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal interface ApplicationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.ViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel.ViewModel): ViewModel
}
