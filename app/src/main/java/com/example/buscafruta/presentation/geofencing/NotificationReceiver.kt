package com.example.buscafruta.presentation.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val fruitName = intent.getStringExtra("fruitName") ?: return
        val notificationHelper = NotificationHelper()
        val fruitIcon = notificationHelper.getFruitIconResource(fruitName)
        notificationHelper.showNotification(context, fruitName, fruitIcon)
    }
}
