package com.ivansadovyi.reminder.presentation.utils

import androidx.databinding.Observable
import com.ivansadovyi.reminder.utils.Disposable

fun Observable.setOnPropertyChangeListener(vararg propertyIds: Int, callback: () -> Unit): Disposable {
	val onPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
			if (propertyIds.contains(propertyId)) {
				callback()
			}
		}
	}

	addOnPropertyChangedCallback(onPropertyChangedCallback)

	return object : Disposable {
		override fun dispose() {
			removeOnPropertyChangedCallback(onPropertyChangedCallback)
		}
	}
}