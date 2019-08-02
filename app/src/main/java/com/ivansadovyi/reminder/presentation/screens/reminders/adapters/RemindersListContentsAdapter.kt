package com.ivansadovyi.reminder.presentation.screens.reminders.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ivansadovyi.reminder.presentation.screens.reminders.ArchiveItem
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ArchiveItemDelegate
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ReminderItemDelegate
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.payloads.ArchiveItemUpdatePayload
import com.ivansadovyi.reminder.presentation.utils.BindableRecyclerViewAdapter
import com.ivansadovyi.reminder.reminder.Reminder

class RemindersListContentsAdapter(
	private val archiveItemDelegateListener: ArchiveItemDelegate.Listener,
	private val reminderDelegateListener: ReminderItemDelegate.Listener
) : AsyncListDifferDelegationAdapter<Any>(DIFF_CALLBACK), BindableRecyclerViewAdapter<Any> {

	init {
		delegatesManager.run {
			addDelegate(ArchiveItemDelegate(archiveItemDelegateListener))
			addDelegate(ReminderItemDelegate(reminderDelegateListener))
		}
	}

	companion object {

		private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {

			override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
				if (oldItem is ArchiveItem && newItem is ArchiveItem) {
					return true
				}

				if (oldItem is Reminder && newItem is Reminder) {
					return oldItem.id == newItem.id
				}

				return false
			}

			override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
				if (oldItem is ArchiveItem && newItem is ArchiveItem) {
					return oldItem.isExpanded == newItem.isExpanded
				}

				return false
			}

			override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
				if (oldItem is ArchiveItem && newItem is ArchiveItem) {
					return if (newItem.isExpanded) {
						ArchiveItemUpdatePayload.Expand
					} else {
						ArchiveItemUpdatePayload.Collapse
					}
				}

				return super.getChangePayload(oldItem, newItem)
			}
		}
	}
}