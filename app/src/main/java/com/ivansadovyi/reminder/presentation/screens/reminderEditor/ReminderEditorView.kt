package com.ivansadovyi.reminder.presentation.screens.reminderEditor

interface ReminderEditorView {

	fun finish()

	fun showTimePicker()

	fun showDatePicker()

	fun showDeleteConfirmation()
}