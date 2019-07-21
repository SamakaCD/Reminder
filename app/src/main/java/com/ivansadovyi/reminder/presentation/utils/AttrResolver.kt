package com.ivansadovyi.reminder.presentation.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes

object AttrResolver {

	@JvmStatic
	fun getColor(context: Context, @AttrRes colorAttr: Int): Int {
		val attrs = intArrayOf(colorAttr)
		val typedArray = context.obtainStyledAttributes(attrs)
		val color = typedArray.getColor(0, Color.BLACK)
		typedArray.recycle()
		return color
	}
}
