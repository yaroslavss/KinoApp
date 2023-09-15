package com.yara.kinoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.yara.kinoapp.di.AppComponent
import com.yara.kinoapp.di.DaggerAppComponent
import com.yara.kinoapp.di.modules.DatabaseModule
import com.yara.kinoapp.di.modules.DomainModule
import com.yara.kinoapp.view.notifications.NotificationConstants.CHANNEL_ID
import com.yara.remote_module.DaggerRemoteComponent

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // name, description and importance of channel
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_DESC
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            // create channel
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            // set description
            mChannel.description = descriptionText
            // get notification manager
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // register channel
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        lateinit var instance: App
            private set

        const val NOTIFICATION_CHANNEL_NAME = "WatchLaterChannel"
        const val NOTIFICATION_CHANNEL_DESC = "KinoApp Notification Channel"
    }
}