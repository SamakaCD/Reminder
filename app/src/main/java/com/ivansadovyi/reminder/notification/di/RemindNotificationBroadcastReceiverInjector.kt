package com.ivansadovyi.reminder.notification.di

import android.content.Context
import com.ivansadovyi.reminder.di.AppDependenciesContainer
import com.ivansadovyi.reminder.notification.RemindNotificationBroadcastReceiver
import com.ivansadovyi.reminder.utils.di.Provider

object RemindNotificationBroadcastReceiverInjector {

	fun inject(instance: RemindNotificationBroadcastReceiver, context: Context) {
		val appDependenciesContainer = (context.applicationContext as Provider<AppDependenciesContainer>).get()
		val reminderDaoProvider = appDependenciesContainer.reminderDaoProvider
		instance.reminderDao = reminderDaoProvider.get()
	}
}