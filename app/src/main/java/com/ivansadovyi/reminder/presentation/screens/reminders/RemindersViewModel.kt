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

	@get:Bindable
	var isArchiveExpanded by ObservableField(fieldId = BR.archiveExpanded, value = false)

	@Bindable("reminders", "archiveExpanded")
	fun getListContents(): ArrayList<Any> {
		return listContentsBuilder.build(reminders, isArchiveExpanded)
	}

	private val listContentsBuilder = RemindersListContentsBuilder()
	private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

	fun onResume() {
		coroutineScope.launch {
			reminders = reminderDao.getReminders()
		}
	}

	fun toggleArchiveExpansion() {
		isArchiveExpanded = !isArchiveExpanded
	}
}