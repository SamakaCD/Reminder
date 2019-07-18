package com.ivansadovyi.reminder.reminder.di

import com.ivansadovyi.reminder.db.AppDatabase
import com.ivansadovyi.reminder.reminder.ReminderDao
import com.ivansadovyi.reminder.utils.di.CachedProvider
import com.ivansadovyi.reminder.utils.di.Provider

class ReminderDaoProvider(private val appDatabaseProvider: Provider<AppDatabase>) : CachedProvider<ReminderDao>() {

	override fun getForCache(): ReminderDao {
		return appDatabaseProvider.get().getReminderDao()
	}
}