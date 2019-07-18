package com.ivansadovyi.reminder.presentation.screens.reminders.di

import com.ivansadovyi.reminder.di.AppDependenciesContainer
import com.ivansadovyi.reminder.presentation.screens.reminders.RemindersActivity
import com.ivansadovyi.reminder.utils.di.Provider

object RemindersActivityInjector {

	fun inject(activity: RemindersActivity) {
		val appDependenciesContainer = (activity.applicationContext as Provider<AppDependenciesContainer>).get()
		val reminderDaoProvider = appDependenciesContainer.reminderDaoProvider
		val viewModelProvider = RemindersViewModelProvider(reminderDaoProvider)
		activity.viewModel = viewModelProvider.get()
	}
}
