package com.ivansadovyi.reminder.presentation.screens.reminders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.presentation.screens.reminders.RemindersListAdapter.ViewHolder
import com.ivansadovyi.reminder.presentation.utils.BindableRecyclerViewAdapter
import com.ivansadovyi.reminder.reminder.Reminder
import com.ivansadovyi.reminder.reminder.ReminderDateFormatter

class RemindersListAdapter(
	private val listener: RemindersListAdapter.Listener
) : RecyclerView.Adapter<ViewHolder>(), BindableRecyclerViewAdapter<Reminder>, View.OnClickListener {

	interface Listener {

		fun onReminderClick(reminder: Reminder)
	}

	private var items = emptyList<Reminder>()

	override fun setItems(items: List<Reminder>) {
		this.items = items
		notifyDataSetChanged()
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val itemView = layoutInflater.inflate(R.layout.item_reminder, parent, false)
		return ViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val reminder = items[position]
		val context = holder.itemView.context
		holder.itemView.tag = position
		holder.itemView.setOnClickListener(this)
		holder.text.text = reminder.text
		holder.time.text = ReminderDateFormatter.format(context, reminder.date)
	}

	override fun onClick(v: View) {
		val position = v.tag as Int
		val reminder = items[position]
		listener.onReminderClick(reminder)
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val text: TextView = itemView.findViewById(R.id.text)
		val time: TextView = itemView.findViewById(R.id.time)
	}
}