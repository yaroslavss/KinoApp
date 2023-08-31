package com.yara.kinoapp.di

import com.yara.kinoapp.di.modules.DatabaseModule
import com.yara.kinoapp.di.modules.DomainModule
import com.yara.kinoapp.viewmodel.HomeFragmentViewModel
import com.yara.kinoapp.viewmodel.SettingsFragmentViewModel
import com.yara.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    // all modules
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}