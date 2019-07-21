package com.ivansadovyi.reminder.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ivansadovyi.reminder.notification.di.RemindNotificationBroadcastReceiverInjector
import com.ivansadovyi.reminder.reminder.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RemindNotificationBroadcastReceiver : BroadcastReceiver() {

	lateinit var reminderDao: ReminderDao

	private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

	override fun onReceive(context: Context, intent: Intent) {
		RemindNotificationBroadcastReceiverInjector.inject(this, context)
		coroutineScope.launch {
			val reminderId = intent.getLongExtra(REMINDER_ID, 0)
			val reminder = reminderDao.getReminderById(reminderId)
			val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			RemindNotification(context, reminder).show(notificationManager)
		}
	}

	companion object {

		const val ACTION_REMIND = "com.ivansadovyi.reminder.action.REMIND"

		private const val REMINDER_ID = "reminderId"

		fun createExtras(reminderId: Long): Bundle {
			return Bundle().apply {
				putLong(REMINDER_ID, reminderId)
			}
		}
	}
}