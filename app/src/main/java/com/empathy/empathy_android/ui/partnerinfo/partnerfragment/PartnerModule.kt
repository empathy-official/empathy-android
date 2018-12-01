package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(PartnerModule.ProvideModule::class))
internal interface PartnerModule {

    @Module
    object ProvideModule {

    }

    @Binds
    @FragmentScope
    fun bindsPartnerChannel(channel: PartnerChannel): PartnerChannelApi

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(PartnerViewModel::class)
    fun bindsPartnerViewModel(viewModel: PartnerViewModel): ViewModel

}