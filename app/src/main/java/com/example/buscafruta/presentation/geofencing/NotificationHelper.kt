package com.example.buscafruta.presentation.geofencing

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationCompat.WearableExtender
import com.example.BuscaFruta.R
import com.example.buscafruta.presentation.activity.MapsActivity

class NotificationHelper {

    fun showNotification(context: Context, fruitName: String, fruitIcon: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "fruit_notification_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificações de Frutas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val mapIntent = Intent(context, MapsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fruitName", fruitName)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val notificationTitle = "Hmm, tem $fruitName perto de você!"
        val notificationDescription = "Clique para ver no mapa."

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(fruitIcon)
            .setContentTitle(notificationTitle)
            .setContentText(notificationDescription)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .extend(WearableExtender())
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }

    fun getFruitIconResource(fruitName: String): Int = when (fruitName) {
        "Amora" -> R.drawable.ic_amora
        "Manga" -> R.drawable.ic_manga
        "Pitanga" -> R.drawable.ic_pitanga
        "Romã" -> R.drawable.ic_roma
        "Abacate" -> R.drawable.ic_abacate
        "Jabuticaba" -> R.drawable.ic_jabuticaba
        else -> R.drawable.ic_frutas
    }
}
