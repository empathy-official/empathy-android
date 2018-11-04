package com.empathy.empathy_android.ui.feed

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.di.qualifier.Main
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
    @IntoMap
    @ViewModelKey(FeedViewModel.ViewModel::class)
    fun bindMainViewModel(mainViewModel: FeedViewModel.ViewModel): ViewModel

}
