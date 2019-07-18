package com.ivansadovyi.reminder

import android.app.Application
import com.ivansadovyi.reminder.di.AppDependenciesContainer
import com.ivansadovyi.reminder.utils.di.Provider

class ReminderApplication : Application(), Provider<AppDependenciesContainer> {

	private val appDependenciesContainer = AppDependenciesContainer(this)

	override fun get(): AppDependenciesContainer {
		return appDependenciesContainer
	}
}