package com.ivansadovyi.reminder.db.converters

import androidx.room.TypeConverter
import java.util.*

object RoomDateConverter {

	@JvmStatic
	@TypeConverter
	fun fromDate(date: Date?): Long? {
		return date?.time
	}

	@JvmStatic
	@TypeConverter
	fun toDate(dateLong: Long?): Date? {
		return if (dateLong != null) {
			Date(dateLong)
		} else {
			null
		}
	}
}
