package com.empathy.empathy_android.ui.partnerinfo.tourfragment

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(TourOrganizationModule.ProvideModule::class))
internal interface TourOrganizationModule {

    @Module
    class ProvideModule {

    }


    @Binds
    @ActivityScope
    fun bindsTourOrganizationChannel(channel: TourOrganizationChannel): TourOrganizationChannelApi

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(TourOrganizationViewModel::class)
    fun bindsInfoViewModel(infoViewModel: TourOrganizationViewModel): ViewModel
}