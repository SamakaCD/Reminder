package com.ivansadovyi.reminder.reminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReminderDao {

	@Query("SELECT * FROM reminders")
	suspend fun getReminders(): List<Reminder>

	@Query("SELECT * FROM reminders WHERE id = :id")
	suspend fun getReminderById(id: Long): Reminder

	@Insert
	suspend fun addReminder(reminder: Reminder): Long

	@Update
	suspend fun updateReminder(reminder: Reminder)

	@Query("DELETE FROM reminders WHERE id = :id")
	suspend fun deleteReminderById(id: Long)
}