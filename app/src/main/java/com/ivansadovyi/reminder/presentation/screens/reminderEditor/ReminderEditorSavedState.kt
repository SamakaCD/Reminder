package com.ivansadovyi.reminder.presentation.screens.reminderEditor

import android.os.Bundle
import java.util.*

class ReminderEditorSavedState(
	var text: String,
	var manualDate: Date?
) {

	constructor(bundle: Bundle) : this(
		text = bundle.getString(TEXT).orEmpty(),
		manualDate = bundle.getLong(MANUAL_DATE).let { date ->
			if (date == 0L) null else Date(date)
		}
	)

	fun writeToBundle(bundle: Bundle) {
		bundle.putString(TEXT, text)
		bundle.putLong(MANUAL_DATE, manualDate?.time ?: 0)
	}

	companion object {
		private const val TEXT = "text"
		private const val MANUAL_DATE = "manualDate"
	}
}
