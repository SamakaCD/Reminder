package com.ivansadovyi.reminder.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ivansadovyi.reminder.R

class RemindNotification(
	private val context: Context,
	private val id: Long,
	private val text: String
) {

	init {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			createChannel()
		}
	}

	fun show(notificationManager: NotificationManager) {
		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setContentTitle(text)
			.setSmallIcon(R.drawable.ic_reminder_notification)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setAutoCancel(true)
			.build()

		notificationManager.notify(id.toInt(), notification)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createChannel() {
		val name = context.getString(R.string.notification_channel_reminders)
		val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(channel)
	}

	companion object {
		private const val CHANNEL_ID = "com.ivansadovyi.notification.REMINDERS"
	}
}