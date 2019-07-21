package com.ivansadovyi.reminder.presentation.utils.bindingAdapters

import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter

object TextViewBindingAdapters {

	@JvmStatic
	@BindingAdapter("drawableTint")
	fun setDrawableTint(textView: TextView, color: Int) {
		textView.compoundDrawables
			.filter { it != null }
			.forEach { DrawableCompat.setTint(it.mutate(), color) }
	}
}
