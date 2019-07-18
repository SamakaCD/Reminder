package com.ivansadovyi.reminder.reminder

import android.content.Context
import android.text.format.DateUtils
import java.util.*

object ReminderDateFormatter {

	@JvmStatic
	fun format(context: Context, date: Date): String {
		return if (DateUtils.isToday(date.time)) {
			DateUtils.formatDateTime(context, date.time, DateUtils.FORMAT_SHOW_TIME)
		} else {
			DateUtils.getRelativeDateTimeString(context, date.time, DateUtils.SECOND_IN_MILLIS,
				2 * DateUtils.DAY_IN_MILLIS, 0).toString()
		}
	}
}