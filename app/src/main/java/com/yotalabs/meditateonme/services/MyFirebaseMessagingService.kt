package com.yotalabs.meditateonme.services

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yotalabs.meditateonme.R
import com.yotalabs.meditateonme.ui.main.MainActivity
import com.yotalabs.meditateonme.util.sendNotification
import timber.log.Timber

/**
 * @author Alexander Khyzhun
 * Created on 02 July, 2019
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Timber.d("MyFirebaseMessagingService | onMessageReceived | ${remoteMessage?.toString()}")

        sendNotification(
            intent = Intent(this, MainActivity::class.java),
            image = R.mipmap.ic_launcher_round,
            title = remoteMessage?.notification?.title ?: "MeditateOnMe",
            description = remoteMessage?.notification?.body ?: "Time to mediate"
        )

    }

}