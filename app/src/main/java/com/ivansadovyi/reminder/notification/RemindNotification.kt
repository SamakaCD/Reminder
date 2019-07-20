package com.ivansadovyi.reminder.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.snoozeReminder.SnoozeReminderActivity

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
		val snoozeIntent = Intent(context, SnoozeReminderActivity::class.java)
		snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		snoozeIntent.putExtras(SnoozeReminderActivity.createExtras(reminderId = id))
		val snoozePendingIntent = PendingIntent.getActivity(context, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
		val snoozeActionText = context.getString(R.string.notification_action_snooze)

		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setContentTitle(text)
			.setSmallIcon(R.drawable.ic_reminder_notification)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setAutoCancel(true)
			.addAction(R.drawable.ic_snooze, snoozeActionText, snoozePendingIntent)
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