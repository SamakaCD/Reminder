package com.ivansadovyi.reminder.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ivansadovyi.reminder.notification.RemindNotificationBroadcastReceiver

open class ReminderAlarmManager(private val context: Context) {

	private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

	fun addOrUpdateAlarm(time: Long, id: Long) {
		val pendingIntent = getPendingIntentForAlarm(id)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
		} else {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
		}
	}

	fun deleteAlarm(id: Long) {
		alarmManager.cancel(getPendingIntentForAlarm(id))
	}

	private fun getPendingIntentForAlarm(id: Long): PendingIntent {
		val intent = Intent(context, RemindNotificationBroadcastReceiver::class.java)
		intent.action = RemindNotificationBroadcastReceiver.ACTION_REMIND
		intent.putExtras(RemindNotificationBroadcastReceiver.createExtras(reminderId = id))
		return PendingIntent.getBroadcast(context, id.toInt(), intent, PendingIntent.FLAG_CANCEL_CURRENT)
	}
}