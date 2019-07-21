package com.ivansadovyi.reminder.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "reminders")
class Reminder(
	var text: String,
	var date: Date,
	var shouldBePinned: Boolean
) : Serializable {

	constructor(
		id: Long,
		text: String,
		date: Date,
		shouldBePinned: Boolean
	) : this(text, date, shouldBePinned) {
		this.id = id
	}

	@PrimaryKey(autoGenerate = true)
	var id = 0L
}
