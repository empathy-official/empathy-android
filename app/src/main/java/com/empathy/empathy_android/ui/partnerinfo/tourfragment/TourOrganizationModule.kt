package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(TourOrganizationModule.ProvideModule::class))
internal interface TourOrganizationModule {

    @Module
    class ProvideModule {

    }


    @Binds
    @FragmentScope
    fun bindsTourOrganizationChannel(channel: TourOrganizationChannel): TourOrganizationChannelApi

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(TourOrganizationViewModel::class)
    fun bindsTourOrganizationViewModel(viewModel: TourOrganizationViewModel): ViewModel
}