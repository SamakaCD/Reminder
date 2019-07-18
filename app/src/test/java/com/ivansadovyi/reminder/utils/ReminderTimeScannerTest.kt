package com.ivansadovyi.reminder.utils

import com.ivansadovyi.reminder.reminder.ReminderTimeScanner
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import org.junit.Test
import java.util.*

class ReminderTimeScannerTest {

	@Test
	fun testScan() {
		val time = ReminderTimeScanner.scan("bla bla 14:30 test test test")
		time shouldNotBe null

		val calendar = Calendar.getInstance()
		calendar.time = time
		calendar.get(Calendar.HOUR_OF_DAY) shouldBe 14
		calendar.get(Calendar.MINUTE) shouldBe 30
	}
}