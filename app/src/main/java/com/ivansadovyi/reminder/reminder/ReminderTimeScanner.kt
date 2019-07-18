package com.ivansadovyi.reminder.reminder

import java.util.*
import java.util.regex.Pattern

object ReminderTimeScanner {

	private val TIME_PATTERN = Pattern.compile("((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])" +
			"(?::[0-5][0-9])?(?:\\\\s?(?:am|AM|pm|PM))?)", Pattern.CASE_INSENSITIVE or Pattern.DOTALL)

	fun scanForTextTime(text: String): String? {
		val timeMatcher = TIME_PATTERN.matcher(text)
		if (!timeMatcher.find()) {
			return null
		}

		return timeMatcher.group(0)
	}

	fun scan(text: String): Date? {
		val time = scanForTextTime(text) ?: return null
		val timeElements = time.split(":")
		if (timeElements.size < 2) {
			return null
		}

		val calendar = Calendar.getInstance()
		calendar.set(Calendar.HOUR_OF_DAY, timeElements[0].toInt())
		calendar.set(Calendar.MINUTE, timeElements[1].toInt())
		return calendar.time
	}
}
