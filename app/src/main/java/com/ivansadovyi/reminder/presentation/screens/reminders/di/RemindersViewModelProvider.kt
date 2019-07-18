package com.ivansadovyi.reminder.presentation.screens.reminders.di

import com.ivansadovyi.reminder.presentation.screens.reminders.RemindersViewModel
import com.ivansadovyi.reminder.reminder.ReminderDao
import com.ivansadovyi.reminder.utils.di.Provider

class RemindersViewModelProvider(
	private val reminderDaoProvider: Provider<ReminderDao>
) : Provider<RemindersViewModel> {

	override fun get(): RemindersViewModel {
		return RemindersViewModel(reminderDaoProvider.get())
	}
}