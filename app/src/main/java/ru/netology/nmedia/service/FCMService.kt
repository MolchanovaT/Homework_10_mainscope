package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.Context
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // TODO: replace this in homework
        println(message.data["content"])

        val id = message.data["recipientId"]?.toLong()
        if (id == AppAuth.getInstance().authStateFlow.value.id) {
            handleMessage(message.data["content"].toString())
        }

        if ((id == 0L) && (id != AppAuth.getInstance().authStateFlow.value.id)) {
            AppAuth.getInstance().sendPushToken()
        }

        if ((id != 0L) && (id != AppAuth.getInstance().authStateFlow.value.id)) {
            AppAuth.getInstance().sendPushToken()
        }
        if (id == null) {
            handleMessage(message.data["content"].toString())
        }

    }

    private fun handleMessage(content: String) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification,
                    content
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    override fun onNewToken(token: String) {
        AppAuth.getInstance().sendPushToken(token)
    }
}
