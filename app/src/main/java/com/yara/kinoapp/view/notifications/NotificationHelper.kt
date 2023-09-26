package com.yara.kinoapp.view.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yara.kinoapp.R
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.receivers.ReminderBroadcast
import com.yara.kinoapp.view.MainActivity
import java.util.Calendar

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

    fun setNotification(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dayOfMonth,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        // create alarm
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }

                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()

            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }

    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        // get AlarmManager
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // create intent
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        // put film into intent
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        // create PendingIntent, set flag FLAG_IMMUTABLE for Android 12+
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        // set alarm
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }
}