package com.yotalabs.meditateonme.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import com.yotalabs.meditateonme.R

/**
 * @author Alexander Khyzhun
 * Created on 10 July, 2019
 */
@SuppressLint("NewApi")
fun Context.sendNotification(
    intent: Intent,
    image: Int,
    title: String,
    description: String
) {
    val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    val channelId = "MeditateOnMeChannelId"
    val notifChannelId = "MeditateOnMeChannelId"
    val notifChannelName = "MeditateOnMeChannelName"
    val id = "10001"

    val mIcon = BitmapFactory.decodeResource(this.resources, image)
    val pendingIntent = PendingIntent.getActivity(
        this,
        5555,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = Notification.Builder(this)
    notificationBuilder.setSmallIcon(image)

    when (Build.VERSION.SDK_INT) {
        in 21..23 -> {
            notificationBuilder
                .setSmallIcon(image)
                .setContentTitle(title)
                .setContentText(description)
        }
        in 24..25 -> {
            notificationBuilder
                .setLargeIcon(mIcon)
                .setContentTitle(title)
                .setContentText(description)
        }
        in 26..27 -> {
            notificationBuilder
                .setLargeIcon(mIcon)
                .setContentTitle(title)
                .setContentText(description)

            val notificationChannel = NotificationChannel(
                notifChannelId,
                notifChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            with(notificationChannel) {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(false)
            }

            notificationBuilder.setChannelId(id)
            notificationManager.createNotificationChannel(notificationChannel)
        }


        in 28..30 -> {
            notificationBuilder
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(image)
                .setChannelId(channelId)
                .build()

            val notificationChannel = NotificationChannel(
                channelId,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            with(notificationChannel) {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(false)
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    notificationBuilder.setContentIntent(pendingIntent)
        .setLights(Color.BLUE, 1000, 1500)
        .setAutoCancel(true)
        .setWhen(System.currentTimeMillis())


    notificationManager.notify(123, notificationBuilder.build())

}