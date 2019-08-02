package com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.reminders.ArchiveItem
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ArchiveItemDelegate.ViewHolder
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.payloads.ArchiveItemUpdatePayload
import com.ivansadovyi.reminder.presentation.utils.AttrResolver

class ArchiveItemDelegate(
	private val listener: Listener
) : AbsListItemAdapterDelegate<ArchiveItem, Any, ViewHolder>(), View.OnClickListener {

	interface Listener {

		fun onArchiveItemClick()
	}

	override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
		return item is ArchiveItem
	}

	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val itemView = layoutInflater.inflate(R.layout.item_archive, parent, false)
		return ViewHolder(itemView)
	}

	override fun onBindViewHolder(item: ArchiveItem, holder: ViewHolder, payloads: MutableList<Any>) {
		val context = holder.itemView.context
		val textColor = if (item.isExpanded) {
			AttrResolver.getColor(context, android.R.attr.colorAccent)
		} else {
			AttrResolver.getColor(context, android.R.attr.textColorPrimary)
		}

		val itemView = holder.itemView as LinearLayout
		itemView.showDividers = if (item.isExpanded) {
			LinearLayout.SHOW_DIVIDER_NONE
		} else {
			LinearLayout.SHOW_DIVIDER_END
		}

		val payload = payloads.firstOrNull()
		if (payload != null) {
			when (payload) {
				is ArchiveItemUpdatePayload.Collapse -> {
					holder.chevron.animate()
						.rotation(0f)
						.start()
				}
				is ArchiveItemUpdatePayload.Expand -> {
					holder.chevron.animate()
						.rotation(180f)
						.start()
				}
			}
		}

		holder.itemView.setOnClickListener(this)
		holder.text.setTextColor(textColor)
	}

	override fun onClick(v: View) {
		listener.onArchiveItemClick()
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val text: TextView = itemView.findViewById(R.id.text)
		val chevron: View = itemView.findViewById(R.id.chevron)
	}
}
