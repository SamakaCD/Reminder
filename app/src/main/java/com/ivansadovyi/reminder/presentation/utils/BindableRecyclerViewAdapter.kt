package com.ivansadovyi.reminder.presentation.utils

interface BindableRecyclerViewAdapter<T> {

	fun setItems(items: List<T>)
}