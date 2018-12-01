package com.empathy.empathy_android.di

import com.empathy.empathy_android.di.scope.ActivityScope
import com.empathy.empathy_android.ui.feed.FeedActivity
import com.empathy.empathy_android.ui.feed.FeedModule
import com.empathy.empathy_android.ui.feeddetail.FeedDetailActivity
import com.empathy.empathy_android.ui.feeddetail.FeedDetailModule
import com.empathy.empathy_android.ui.feedinput.FeedInputActivity
import com.empathy.empathy_android.ui.feedinput.FeedInputModule
import com.empathy.empathy_android.ui.partnerinfo.PartnerInfoActivity
import com.empathy.empathy_android.ui.partnerinfo.PartnerInfoModule
import com.empathy.empathy_android.ui.login.LoginActivity
import com.empathy.empathy_android.ui.login.LoginModule
import com.empathy.empathy_android.ui.login.MapModule
import com.empathy.empathy_android.ui.mypage.MyFeedsActivity
import com.empathy.empathy_android.ui.mypage.MyFeedModule
import com.empathy.empathy_android.ui.tmap.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeedModule::class])
    abstract fun feedActivity(): FeedActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun loginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun mapActivity(): MapActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MyFeedModule::class])
    abstract fun myPageActivity(): MyFeedsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PartnerInfoModule::class])
    abstract fun infoActivity(): PartnerInfoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeedDetailModule::class])
    abstract fun feedDetailActivity(): FeedDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeedInputModule::class])
    abstract fun feedInputActivity(): FeedInputActivity

}
