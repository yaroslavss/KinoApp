package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.di.AppComponent
import com.yara.kinoapp.di.DaggerAppComponent
import com.yara.kinoapp.di.modules.DatabaseModule
import com.yara.kinoapp.di.modules.DomainModule
import com.yara.remote_module.DaggerRemoteComponent
import com.yara.remote_module.RemoteModule
import com.yara.remote_module.RemoteProvider

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        // create component
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}