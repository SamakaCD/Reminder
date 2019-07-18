package com.ivansadovyi.reminder.presentation.screens.reminderEditor.di

import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorActivity
import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorView
import com.ivansadovyi.reminder.utils.di.Provider

class ReminderEditorViewProvider(private val activity: ReminderEditorActivity) : Provider<ReminderEditorView> {

	override fun get(): ReminderEditorView {
		return activity
	}
}