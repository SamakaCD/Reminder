package com.ivansadovyi.reminder.reminder.di

import android.content.Context
import com.ivansadovyi.reminder.reminder.ReminderAlarmManager
import com.ivansadovyi.reminder.utils.di.CachedProvider
import com.ivansadovyi.reminder.utils.di.Provider

class ReminderAlarmManagerProvider(
	private val contextProvider: Provider<Context>
) : CachedProvider<ReminderAlarmManager>() {

	override fun getForCache(): ReminderAlarmManager {
		return ReminderAlarmManager(contextProvider.get())
	}
}