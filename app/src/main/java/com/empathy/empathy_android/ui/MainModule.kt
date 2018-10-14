package com.empathy.empathy_android.ui

import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.di.qualifier.Main
import dagger.Module
import dagger.Provides
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

}
