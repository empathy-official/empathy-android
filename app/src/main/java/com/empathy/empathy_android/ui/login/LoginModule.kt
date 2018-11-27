package com.empathy.empathy_android.ui.login

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.qualifier.LocFilter
import com.empathy.empathy_android.di.qualifier.Login
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MapModule.ProvideModule::class])
internal interface LoginModule {

    @Module
    class ProvideModule {
        @Provides
        @ActivityScope
        @Login
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    }

    @Binds
    @ActivityScope
    @LocFilter
    fun bindsLocationFilter(locationFilter: LocationFilter): LocationFilterApi

    @Binds
    @ActivityScope
    fun bindsLoginChannel(channel: LoginChannel): LoginChannelApi

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(LoginViewModel.ViewModel::class)
    fun bindsLoginViewModel(loginViewModel: LoginViewModel.ViewModel): ViewModel

}
