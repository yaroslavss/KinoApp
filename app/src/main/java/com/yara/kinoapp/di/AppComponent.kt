package com.yara.kinoapp.di

import com.yara.kinoapp.di.modules.DatabaseModule
import com.yara.kinoapp.di.modules.DomainModule
import com.yara.kinoapp.di.modules.RemoteModule
import com.yara.kinoapp.viewmodel.HomeFragmentViewModel
import com.yara.kinoapp.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    // all modules
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}