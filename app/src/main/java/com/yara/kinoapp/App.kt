package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.di.AppComponent
import com.yara.kinoapp.di.DaggerAppComponent
import com.yara.kinoapp.di.modules.DatabaseModule
import com.yara.kinoapp.di.modules.DomainModule
import com.yara.kinoapp.di.modules.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        // create component
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}