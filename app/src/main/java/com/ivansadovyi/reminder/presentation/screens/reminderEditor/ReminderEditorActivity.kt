package com.ivansadovyi.reminder.presentation.screens.reminderEditor

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.databinding.ActivityAddReminderBinding
import com.ivansadovyi.reminder.presentation.screens.reminderEditor.di.ReminderEditorActivityInjector
import com.ivansadovyi.reminder.reminder.Reminder
import java.util.*

class ReminderEditorActivity : Activity(), ReminderEditorView, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

	lateinit var viewModel: ReminderEditorViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ReminderEditorActivityInjector.inject(this)

		intent.extras?.let { extras ->
			val reminder = extras.getSerializable(REMINDER) as Reminder?
			viewModel.onCreate(reminder)

			setTitle(R.string.reminder_editor_title_edit_reminder)
		}

		if (savedInstanceState != null) {
			viewModel.onRestore(ReminderEditorSavedState(savedInstanceState))
		}

		val binding = DataBindingUtil.setContentView<ActivityAddReminderBinding>(this, R.layout.activity_add_reminder)
		binding.viewModel = viewModel
		binding.view = this
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		viewModel.getSavedState().writeToBundle(outState)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		if (viewModel.isInEditMode()) {
			menuInflater.inflate(R.menu.edit_reminder, menu)
		}

		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.delete -> showDeleteConfirmation()
		}

		return super.onOptionsItemSelected(item)
	}

	override fun showTimePicker() {
		val calendar = Calendar.getInstance()
		viewModel.getDate()?.let { calendar.time = it }

		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)
		TimePickerDialog(this, this, hour, minute, true).show()
	}

	override fun showDatePicker() {
		val calendar = Calendar.getInstance()
		viewModel.getDate()?.let { calendar.time = it }

		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
		DatePickerDialog(this, this, year, month, dayOfMonth).show()
	}

	override fun showDeleteConfirmation() {
		AlertDialog.Builder(this)
			.setMessage(R.string.reminder_editor_delete_confirmation_text)
			.setPositiveButton(android.R.string.yes) { _, _ -> viewModel.deleteReminder() }
			.setNegativeButton(android.R.string.no, null)
			.show()
	}

	override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
		viewModel.setTime(hourOfDay, minute)
		showDatePicker()
	}

	override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
		viewModel.setDate(year, month, dayOfMonth)
	}

	companion object {

		private const val REMINDER = "reminder"

		fun createExtras(reminder: Reminder? = null): Bundle {
			val extras = Bundle()
			extras.putSerializable(REMINDER, reminder)
			return extras
		}
	}
}