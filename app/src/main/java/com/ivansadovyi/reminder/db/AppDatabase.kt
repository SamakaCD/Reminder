package com.ivansadovyi.reminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivansadovyi.reminder.db.converters.RoomDateConverter
import com.ivansadovyi.reminder.reminder.Reminder
import com.ivansadovyi.reminder.reminder.ReminderDao

@Database(entities = [Reminder::class], version = 1)
@TypeConverters(RoomDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun getReminderDao(): ReminderDao

	companion object {
		const val NAME = "app_database"
	}
}
