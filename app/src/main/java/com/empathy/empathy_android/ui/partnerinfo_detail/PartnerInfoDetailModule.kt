package com.empathy.empathy_android.ui.partnerinfo_detail

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = arrayOf(PartnerInfoDetailModule.ProvideModule::class))
internal interface PartnerInfoDetailModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(PartnerInfoDetailViewModel::class)
    fun bindsPartnerInfoDetailViewModel(viewModel: PartnerInfoDetailViewModel): ViewModel
}
