package com.ivansadovyi.reminder.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle

class UnpinReminderNotificationBroadcastReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		val reminderId = intent.getLongExtra(REMINDER_ID, 0)
		notificationManager.cancel(reminderId.toInt())
	}

	companion object {

		const val ACTION_UNPIN_REMINDER = "com.ivansadovyi.reminder.action.UNPIN_REMINDER"

		private const val REMINDER_ID = "reminderId"

		fun createExtras(reminderId: Long): Bundle {
			return Bundle().apply {
				putLong(REMINDER_ID, reminderId)
			}
		}
	}
}