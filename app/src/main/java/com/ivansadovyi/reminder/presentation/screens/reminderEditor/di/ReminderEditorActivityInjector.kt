package com.ivansadovyi.reminder.presentation.screens.reminderEditor.di

import com.ivansadovyi.reminder.di.AppDependenciesContainer
import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorActivity
import com.ivansadovyi.reminder.utils.di.Provider

object ReminderEditorActivityInjector {

	fun inject(activity: ReminderEditorActivity) {
		val appDependenciesContainer = (activity.application as Provider<AppDependenciesContainer>).get()
		val reminderDaoProvider = appDependenciesContainer.reminderDaoProvider
		val reminderAlarmManagerProvider = appDependenciesContainer.reminderAlarmManagerProvider
		val addReminderViewProvider = ReminderEditorViewProvider(activity)
		val viewModelProvider = ReminderEditorViewModelProvider(addReminderViewProvider, reminderDaoProvider,
			reminderAlarmManagerProvider)
		activity.viewModel = viewModelProvider.get()
	}
}
