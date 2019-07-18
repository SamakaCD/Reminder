package com.ivansadovyi.reminder.utils.di

class ValueProvider<T>(private val value: T) : Provider<T> {

	override fun get(): T {
		return value
	}
}