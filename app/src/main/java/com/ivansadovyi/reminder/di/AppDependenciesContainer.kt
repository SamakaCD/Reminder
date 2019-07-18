package com.ivansadovyi.reminder.di

import android.content.Context
import com.ivansadovyi.reminder.db.di.AppDatabaseProvider
import com.ivansadovyi.reminder.reminder.di.ReminderAlarmManagerProvider
import com.ivansadovyi.reminder.reminder.di.ReminderDaoProvider
import com.ivansadovyi.reminder.utils.di.ValueProvider

class AppDependenciesContainer(context: Context) {

	val applicationContextProvider = ValueProvider(context)

	val appDatabaseProvider = AppDatabaseProvider(applicationContextProvider)

	val reminderDaoProvider = ReminderDaoProvider(appDatabaseProvider)

	val reminderAlarmManagerProvider = ReminderAlarmManagerProvider(applicationContextProvider)
}
