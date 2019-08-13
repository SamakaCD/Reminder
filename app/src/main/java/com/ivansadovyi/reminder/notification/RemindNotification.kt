package com.ivansadovyi.reminder.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.snoozeReminder.SnoozeReminderActivity
import com.ivansadovyi.reminder.reminder.Reminder

class RemindNotification(private val context: Context, private val reminder: Reminder) {

	init {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			createChannel()
		}
	}

	fun show(notificationManager: NotificationManagerCompat) {
		showWearableNotification(notificationManager)

		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setContentTitle(reminder.text)
			.setSmallIcon(R.drawable.ic_reminder_notification)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setAutoCancel(true)
			.setOngoing(reminder.shouldBePinned)
			.setGroup(WEAR_GROUP)
			.setGroupSummary(true)
			.addAction(R.drawable.ic_snooze, context.getString(R.string.notification_action_snooze), createSnoozePendingIntent())
			.apply {
				if (reminder.shouldBePinned) {
					addAction(R.drawable.ic_unpin, context.getString(R.string.notification_action_unpin), createUnpinPendingIntent())
				}
			}
			.build()

		notificationManager.notify(reminder.id.toInt(), notification)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createChannel() {
		val name = context.getString(R.string.notification_channel_reminders)
		val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(channel)
	}

	private fun createSnoozePendingIntent(): PendingIntent {
		val intent = Intent(context, SnoozeReminderActivity::class.java)
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		intent.putExtras(SnoozeReminderActivity.createExtras(reminder.id))
		return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
	}

	private fun createUnpinPendingIntent(): PendingIntent {
		val intent = Intent(context, UnpinReminderNotificationBroadcastReceiver::class.java)
		intent.action = UnpinReminderNotificationBroadcastReceiver.ACTION_UNPIN_REMINDER
		intent.putExtras(UnpinReminderNotificationBroadcastReceiver.createExtras(reminder.id))
		return PendingIntent.getBroadcast(context, reminder.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
	}

	private fun showWearableNotification(notificationManager: NotificationManagerCompat) {
		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setContentTitle(reminder.text)
			.setSmallIcon(R.drawable.ic_reminder_notification)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setAutoCancel(true)
			.setGroup(WEAR_GROUP)
			.setGroupSummary(false)
			.addAction(R.drawable.ic_snooze, context.getString(R.string.notification_action_snooze), createSnoozePendingIntent())
			.apply {
				if (reminder.shouldBePinned) {
					addAction(R.drawable.ic_unpin, context.getString(R.string.notification_action_unpin), createUnpinPendingIntent())
				}
			}
			.build()

		notificationManager.notify(reminder.id.toInt().inv(), notification)
	}

	companion object {
		private const val CHANNEL_ID = "com.ivansadovyi.notification.REMINDERS"
		private const val WEAR_GROUP = "com.ivansadovyi.notification.WEAR_GROUP"
	}
}