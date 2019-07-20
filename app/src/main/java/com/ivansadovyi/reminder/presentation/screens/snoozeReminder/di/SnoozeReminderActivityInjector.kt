package com.ivansadovyi.reminder.presentation.screens.snoozeReminder.di

import com.ivansadovyi.reminder.di.AppDependenciesContainer
import com.ivansadovyi.reminder.presentation.screens.snoozeReminder.SnoozeReminderActivity
import com.ivansadovyi.reminder.utils.di.Provider

object SnoozeReminderActivityInjector {

	fun inject(activity: SnoozeReminderActivity) {
		val appDependenciesContainer = (activity.applicationContext as Provider<AppDependenciesContainer>).get()
		val reminderDaoProvider = appDependenciesContainer.reminderDaoProvider
		val reminderAlarmManagerProvider = appDependenciesContainer.reminderAlarmManagerProvider
		activity.reminderDao = reminderDaoProvider.get()
		activity.reminderAlarmManager = reminderAlarmManagerProvider.get()
	}
}