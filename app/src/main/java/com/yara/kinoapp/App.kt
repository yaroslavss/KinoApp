package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.domain.Interactor

class App : Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()

        // init App
        instance = this
        // init repo
        repo = MainRepository()
        // init interactor
        interactor = Interactor(repo)
    }

    companion object {
        // static
        lateinit var instance: App
            // private setter
            private set
    }
}