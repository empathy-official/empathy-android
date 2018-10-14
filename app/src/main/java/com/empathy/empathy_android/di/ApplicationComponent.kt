package com.empathy.empathy_android.di

import com.empathy.empathy_android.EmpathyApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApplicationModule::class,
        ApplicationRepositoryModule::class,
        ApplicationViewModelModule::class)
)
internal interface ApplicationComponent: AndroidInjector<EmpathyApp> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<EmpathyApp>()
}