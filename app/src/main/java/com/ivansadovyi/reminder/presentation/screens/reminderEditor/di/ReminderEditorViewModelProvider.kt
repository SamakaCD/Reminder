package com.ivansadovyi.reminder.presentation.screens.reminderEditor.di

import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorView
import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorViewModel
import com.ivansadovyi.reminder.reminder.ReminderAlarmManager
import com.ivansadovyi.reminder.reminder.ReminderDao
import com.ivansadovyi.reminder.utils.di.Provider

class ReminderEditorViewModelProvider(
	private val viewProvider: Provider<ReminderEditorView>,
	private val reminderDaoProvider: Provider<ReminderDao>,
	private val reminderAlarmManagerProvider: Provider<ReminderAlarmManager>
) :
	Provider<ReminderEditorViewModel> {

	override fun get(): ReminderEditorViewModel {
		return ReminderEditorViewModel(viewProvider.get(), reminderDaoProvider.get(), reminderAlarmManagerProvider.get())
	}
}