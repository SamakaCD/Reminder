package com.ivansadovyi.reminder.utils.di

interface Provider<T> {

	fun get(): T
}
