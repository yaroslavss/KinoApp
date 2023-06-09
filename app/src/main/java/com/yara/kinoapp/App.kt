package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.di.AppComponent
import com.yara.kinoapp.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        // create component
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}