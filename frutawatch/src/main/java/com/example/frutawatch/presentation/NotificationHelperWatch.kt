package com.example.frutawatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.frutawatch.model.FruitTree
import com.example.frutawatch.model.MockFruitTrees
import com.example.frutawatch.presentation.MapsActivity
import com.google.android.gms.maps.model.LatLng

class NotificationHelperWatch(private val context: Context) {

    fun triggerNotification(fruit: FruitTree) {
        val intent = Intent(context, MapsActivity::class.java).apply {
            putExtra("fruit_name", fruit.nome)
            putExtra("fruit_location", LatLng(fruit.localizacoes.first().latitude, fruit.localizacoes.first().longitude))
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1

        createNotificationChannelIfNecessary(notificationManager)

        val notification = NotificationCompat.Builder(context, "fruits_channel")
            .setContentTitle("Hmm, tem ${fruit.nome} perto de você!")
            .setContentText("Clique para ver no mapa.")
            .setSmallIcon(fruit.icone)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, fruit.icone))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    // Método para disparar notificação com base no ID do geofence
    fun triggerNotificationByGeofence(requestId: String) {
        val fruit = MockFruitTrees.allFruitTrees.firstOrNull { it.nome == requestId } ?: return
        triggerNotification(fruit)
    }

    private fun createNotificationChannelIfNecessary(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "fruits_channel",
                "Fruits Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}
