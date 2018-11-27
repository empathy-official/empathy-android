package com.empathy.empathy_android.ui.partnerinfo

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(PartnerInfoModule.ProvideModule::class))
internal interface PartnerInfoModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(PartnerInfoViewModel::class)
    fun bindsInfoViewModel(infoViewModel: PartnerInfoViewModel): ViewModel
}