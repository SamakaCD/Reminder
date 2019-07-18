package com.ivansadovyi.reminder.db.di

import android.content.Context
import androidx.room.Room
import com.ivansadovyi.reminder.db.AppDatabase
import com.ivansadovyi.reminder.utils.di.CachedProvider
import com.ivansadovyi.reminder.utils.di.Provider

class AppDatabaseProvider(private val contextProvider: Provider<Context>) : CachedProvider<AppDatabase>() {

	override fun getForCache(): AppDatabase {
		return Room.databaseBuilder(contextProvider.get(), AppDatabase::class.java, AppDatabase.NAME)
			.build()
	}
}