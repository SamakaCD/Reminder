package com.ivansadovyi.reminder.presentation.screens.reminders

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ivansadovyi.reminder.BR
import com.ivansadovyi.reminder.presentation.utils.ObservableField
import com.ivansadovyi.reminder.reminder.Reminder
import com.ivansadovyi.reminder.reminder.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RemindersViewModel(
	private val reminderDao: ReminderDao
) : BaseObservable() {

	@get:Bindable
	var reminders by ObservableField(fieldId = BR.reminders, value = emptyList<Reminder>())

	private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

	fun onResume() {
		coroutineScope.launch {
			reminders = reminderDao.getReminders()
		}
	}
}