package com.mar.project.drinkingreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class AlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("Alarm Bell", "Alarm just fired from broadcast receiver")

        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("thisIsForMyFragment"))
    }
}