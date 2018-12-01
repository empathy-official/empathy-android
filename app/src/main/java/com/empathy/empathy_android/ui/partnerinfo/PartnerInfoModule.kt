package com.empathy.empathy_android.ui.partnerinfo

import androidx.lifecycle.ViewModel
import com.empathy.empathy_android.di.key.ViewModelKey
import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.di.scope.FragmentScope
import com.empathy.empathy_android.ui.partnerinfo.partnerfragment.PartnerFragment
import com.empathy.empathy_android.ui.partnerinfo.partnerfragment.PartnerModule
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationFragment
import com.empathy.empathy_android.ui.partnerinfo.tourfragment.TourOrganizationModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module(includes = arrayOf(PartnerInfoModule.ProvideModule::class))
internal interface PartnerInfoModule {

    @Module
    class ProvideModule {

    }

    @ContributesAndroidInjector(modules = [PartnerModule::class])
    @FragmentScope
    fun providePartnerFragment(): PartnerFragment

    @ContributesAndroidInjector(modules = [TourOrganizationModule::class])
    @FragmentScope
    fun provideTourOrganizationFragment(): TourOrganizationFragment

    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(PartnerInfoViewModel::class)
    fun bindsInfoViewModel(infoViewModel: PartnerInfoViewModel): ViewModel
}