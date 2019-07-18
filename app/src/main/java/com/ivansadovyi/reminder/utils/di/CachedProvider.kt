package com.ivansadovyi.reminder.utils.di

abstract class CachedProvider<T> : Provider<T> {

	private var instance: T? = null

	override fun get(): T {
		if (instance == null) {
			instance = getForCache()
		}

		return instance!!
	}

	abstract fun getForCache(): T
}