package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.di.DI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // add context
            androidContext(this@App)
            // add logging
            androidLogger()
            // load modules
            modules(listOf(DI.mainModule))
        }
    }
}