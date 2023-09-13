package com.yara.kinoapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.yara.kinoapp.R

class ConnectionChecker : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // if no intent return
        if (intent == null) return
        // check intent
        when (intent.action) {
            // low battery
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, R.string.toast_battery_low, Toast.LENGTH_SHORT).show()
            // power connected
            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context, R.string.toast_power_connected, Toast.LENGTH_SHORT).show()
        }
    }
}