package com.ivansadovyi.reminder.presentation.screens.snoozeReminder

import android.app.*
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.snoozeReminder.di.SnoozeReminderActivityInjector
import com.ivansadovyi.reminder.reminder.ReminderAlarmManager
import com.ivansadovyi.reminder.reminder.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class SnoozeReminderActivity : Activity() {

	lateinit var reminderDao: ReminderDao

	lateinit var reminderAlarmManager: ReminderAlarmManager

	private lateinit var dialog: BottomSheetDialog

	private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		SnoozeReminderActivityInjector.inject(this)
		window.setDimAmount(0f)
		showDialog()
	}

	fun onSnoozeFor15MinsClick(v: View) {
		snoozeReminderFor(15 * DateUtils.MINUTE_IN_MILLIS)
	}

	fun onSnoozeFor1HourClick(v: View) {
		snoozeReminderFor(DateUtils.HOUR_IN_MILLIS)
	}

	fun onSnoozeUpToClick(v: View) {
		AlertDialog.Builder(this)
			.setTitle(R.string.snooze_reminder_up_to)
			.setView(R.layout.dialog_snooze_up_to)
			.setPositiveButton(android.R.string.ok) { dialog, _ ->
				val alertDialog = dialog as AlertDialog
				val hoursEditText = alertDialog.findViewById<EditText>(R.id.hours)
				val minutesEditText = alertDialog.findViewById<EditText>(R.id.minutes)
				val hours = hoursEditText.text.toString().toIntOrNull() ?: 0
				val minutes = minutesEditText.text.toString().toIntOrNull() ?: 0
				val snoozeForMillis = hours * DateUtils.HOUR_IN_MILLIS + minutes * DateUtils.MINUTE_IN_MILLIS
				snoozeReminderFor(snoozeForMillis)
			}
			.setNegativeButton(android.R.string.cancel, null)
			.show()
	}

	fun onRemindAfterClick(v: View) {
		showTimePickerDialog(onTimeSet = { time ->
			showDatePickerDialog(time = time, onDateSet = { date ->
				val snoozeForMillis = date.time - Date().time
				snoozeReminderFor(snoozeForMillis)
			})
		})
	}

	private fun cancelRemindNotification(reminderId: Long) {
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.cancel(reminderId.toInt())
	}

	private fun showDialog() {
		dialog = BottomSheetDialog(this)
		dialog.setContentView(R.layout.activity_snooze_reminder)
		dialog.setOnDismissListener { finish() }
		dialog.setOnCancelListener { finish() }
		dialog.show()
	}

	private fun snoozeReminderFor(timeMillis: Long) {
		coroutineScope.launch {
			val reminderId = intent.getLongExtra(REMINDER_ID, 0)
			val reminder = reminderDao.getReminderById(reminderId)
			reminder.date.time = Date().time + timeMillis
			reminderDao.updateReminder(reminder)
			reminderAlarmManager.addOrUpdateAlarm(reminder.date.time, reminderId)
			cancelRemindNotification(reminderId)
			dialog.dismiss()
			finish()
		}
	}

	private fun showTimePickerDialog(onTimeSet: (Date) -> Unit) {
		val calendar = Calendar.getInstance()
		val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
			calendar.set(Calendar.MINUTE, minute)
			onTimeSet(calendar.time)
		}

		val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)
		TimePickerDialog(this, timeListener, hourOfDay, minute, true).show()
	}

	private fun showDatePickerDialog(time: Date, onDateSet: (Date) -> Unit) {
		val calendar = Calendar.getInstance()
		val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
			calendar.time = time
			calendar.set(year, month, dayOfMonth)
			onDateSet(calendar.time)
		}
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
		DatePickerDialog(this, dateListener, year, month, dayOfMonth).show()
	}

	companion object {

		private const val REMINDER_ID = "reminderId"

		fun createExtras(reminderId: Long) : Bundle {
			return Bundle().apply {
				putLong(REMINDER_ID, reminderId)
			}
		}
	}
}