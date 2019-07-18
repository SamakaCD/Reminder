package com.ivansadovyi.reminder.presentation.utils.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivansadovyi.reminder.presentation.utils.BindableRecyclerViewAdapter

object RecyclerViewBindingAdapters {

	@JvmStatic
	@BindingAdapter("items")
	@Suppress("UNCHECKED_CAST")
	fun <T> setItems(recyclerView: RecyclerView, items: List<T>) {
		if (recyclerView.adapter !is BindableRecyclerViewAdapter<*>) {
			throw IllegalArgumentException("Adapter of RecyclerView should implement BindableRecyclerViewAdapter interface")
		}

		(recyclerView.adapter as BindableRecyclerViewAdapter<T>).setItems(items)
	}
}
