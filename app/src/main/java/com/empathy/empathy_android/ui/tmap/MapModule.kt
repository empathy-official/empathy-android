package com.empathy.empathy_android.ui.login

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = arrayOf(MapModule.ProvideModule::class))
internal interface MapModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(MapViewModel.ViewModel::class)
    fun bindMapViewModel(mapViewModel: MapViewModel.ViewModel): ViewModel

}
