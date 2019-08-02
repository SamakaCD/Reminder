package com.ivansadovyi.reminder.presentation.screens.reminders

import android.text.format.DateUtils
import com.ivansadovyi.reminder.reminder.Reminder

class RemindersListContentsBuilder {

	fun build(reminders: List<Reminder>, isArchiveExpanded: Boolean): ArrayList<Any> {
		val result = ArrayList<Any>()
		buildArchive(result, reminders, isArchiveExpanded)
		buildRestReminders(result, reminders)
		return result
	}

	private fun buildArchive(result: MutableList<Any>, reminders: List<Reminder>, isArchiveExpanded: Boolean) {
		val currentMillis = System.currentTimeMillis()
		val archiveReminders = reminders
			.filter { it.date.time < currentMillis && !DateUtils.isToday(it.date.time) }

		if (archiveReminders.isNotEmpty()) {
			result.add(ArchiveItem(isArchiveExpanded))
		}

		if (isArchiveExpanded) {
			result.addAll(archiveReminders)
		}
	}

	private fun buildRestReminders(result: MutableList<Any>, reminders: List<Reminder>) {
		val currentMillis = System.currentTimeMillis()
		reminders
			.filter { it.date.time > currentMillis || DateUtils.isToday(it.date.time) }
			.forEach { result.add(it) }
	}
}
