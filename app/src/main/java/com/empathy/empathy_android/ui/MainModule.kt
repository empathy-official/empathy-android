package com.empathy.empathy_android.ui

import android.arch.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.di.qualifier.Main
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainModule.ProvideModule::class])
internal interface MainModule {

    @Module
    class ProvideModule {
        @Provides
        @ActivityScope
        @Main
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(MainViewModel.ViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel.ViewModel): ViewModel

}
