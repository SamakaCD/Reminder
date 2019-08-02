package com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ReminderItemDelegate.ViewHolder
import com.ivansadovyi.reminder.reminder.Reminder
import com.ivansadovyi.reminder.reminder.ReminderDateFormatter

class ReminderItemDelegate(
	private val listener: Listener
) : AbsListItemAdapterDelegate<Reminder, Any, ViewHolder>(), View.OnClickListener {

	interface Listener {

		fun onReminderClick(reminder: Reminder)
	}

	override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
		return item is Reminder
	}

	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val itemView = layoutInflater.inflate(R.layout.item_reminder, parent, false)
		return ViewHolder(itemView)
	}

	override fun onBindViewHolder(reminder: Reminder, holder: ViewHolder, payloads: MutableList<Any>) {
		val context = holder.itemView.context
		holder.itemView.tag = reminder
		holder.itemView.setOnClickListener(this)
		holder.text.text = reminder.text
		holder.time.text = ReminderDateFormatter.format(context, reminder.date)
	}

	override fun onClick(v: View) {
		val reminder = v.tag as Reminder
		listener.onReminderClick(reminder)
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val text: TextView = itemView.findViewById(R.id.text)
		val time: TextView = itemView.findViewById(R.id.time)
	}
}