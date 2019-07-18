package com.ivansadovyi.reminder.presentation.screens.reminderEditor

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ivansadovyi.reminder.BR
import com.ivansadovyi.reminder.presentation.utils.ObservableField
import com.ivansadovyi.reminder.reminder.Reminder
import com.ivansadovyi.reminder.reminder.ReminderAlarmManager
import com.ivansadovyi.reminder.reminder.ReminderDao
import com.ivansadovyi.reminder.reminder.ReminderTimeScanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class ReminderEditorViewModel(
	private val view: ReminderEditorView,
	private val reminderDao: ReminderDao,
	private val reminderAlarmManager: ReminderAlarmManager
) : BaseObservable() {

	@get:Bindable
	var text by ObservableField(fieldId = BR.text, value = "")

	@get:Bindable
	var manualDate: Date? by ObservableField(fieldId = BR.manualDate, value = null)

	@Bindable("text")
	fun getSmartDate(): Date? {
		return ReminderTimeScanner.scan(text)
	}

	@Bindable("manualDate", "smartDate")
	fun getDate(): Date? {
		return manualDate ?: getSmartDate()
	}

	fun getSavedState(): ReminderEditorSavedState {
		return ReminderEditorSavedState(
			text = text,
			manualDate = manualDate
		)
	}

	fun getReminder(): Reminder {
		val date = getDate() ?: throw IllegalStateException()
		return Reminder(
			id = reminderId ?: 0,
			text = getTextWithoutSmartDate(),
			date = date
		)
	}

	fun getTextWithoutSmartDate(): String {
		var text = text
		val smartDateAsText = ReminderTimeScanner.scanForTextTime(text)
		if (smartDateAsText != null && smartDateAsText.length < text.trim().length) {
			text = text.replace(smartDateAsText, "", ignoreCase = true)
		}

		return text.trim()
	}

	@Bindable("manualDate", "text")
	fun isSavingAllowed(): Boolean {
		return getDate() != null && getTextWithoutSmartDate().isNotBlank()
	}

	fun isInEditMode(): Boolean {
		return reminderId != null
	}

	private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
	private var reminderId: Long? = null

	fun onCreate(reminder: Reminder?) {
		if (reminder != null) {
			text = reminder.text
			manualDate = reminder.date
			reminderId = reminder.id
		}
	}

	fun onRestore(savedState: ReminderEditorSavedState) {
		text = savedState.text
		manualDate = savedState.manualDate
	}

	fun setTime(hourOfDay: Int, minute: Int) {
		val calendar = Calendar.getInstance()
		getDate()?.let { calendar.time = it }
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
		calendar.set(Calendar.MINUTE, minute)
		manualDate = calendar.time
	}

	fun setDate(year: Int, month: Int, dayOfMonth: Int) {
		val calendar = Calendar.getInstance()
		getDate()?.let { calendar.time = it }
		calendar.set(year, month, dayOfMonth)
		manualDate = calendar.time
	}

	fun saveReminder() {
		coroutineScope.launch {
			val reminderId = if (isInEditMode()) {
				reminderDao.updateReminder(getReminder())
				this@ReminderEditorViewModel.reminderId!!
			} else {
				reminderDao.addReminder(getReminder())
			}

			reminderAlarmManager.addOrUpdateAlarm(getDate()!!.time, reminderId)
			view.finish()
		}
	}

	fun deleteReminder() {
		coroutineScope.launch {
			reminderId?.let { reminderId ->
				reminderDao.deleteReminderById(reminderId)
				reminderAlarmManager.deleteAlarm(reminderId)
			}

			view.finish()
		}
	}
}
