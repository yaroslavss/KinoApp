package com.yara.kinoapp.view.notifications

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yara.kinoapp.R
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.view.MainActivity

object NotificationHelper {
    // notification ID
    private var notifyId = 0
    private var filmName = ""

    @SuppressLint("MissingPermission")
    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE

        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, flags)

        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_outline_watch_later_24)
            setContentTitle(R.string.notification_title.toString())
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        // set notification ID
        if (filmName != film.title) {
            notifyId++
        }
        filmName = film.title

        Glide.with(context)
            .asBitmap()
            .load(film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                // proceed after bitmap loaded
                @SuppressLint("MissingPermission")
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // create 'big picture' notification
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    // update notification
                    notificationManager.notify(notifyId, builder.build())
                }
            })
        // send just created notification
        notificationManager.notify(notifyId, builder.build())
    }
}