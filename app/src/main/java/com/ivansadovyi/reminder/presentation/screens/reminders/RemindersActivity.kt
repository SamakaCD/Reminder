package com.ivansadovyi.reminder.presentation.screens.reminders

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ivansadovyi.reminder.R
import com.ivansadovyi.reminder.databinding.ActivityRemindersBinding
import com.ivansadovyi.reminder.presentation.screens.reminderEditor.ReminderEditorActivity
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.RemindersListContentsAdapter
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ArchiveItemDelegate
import com.ivansadovyi.reminder.presentation.screens.reminders.adapters.delegates.ReminderItemDelegate
import com.ivansadovyi.reminder.presentation.screens.reminders.di.RemindersActivityInjector
import com.ivansadovyi.reminder.reminder.Reminder
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : Activity(), ArchiveItemDelegate.Listener, ReminderItemDelegate.Listener {

    lateinit var viewModel: RemindersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RemindersActivityInjector.inject(this)
        val binding = DataBindingUtil.setContentView<ActivityRemindersBinding>(this, R.layout.activity_reminders)
        binding.viewModel = viewModel
        binding.activity = this
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    fun navigateToAddReminder() {
        val intent = Intent(this, ReminderEditorActivity::class.java)
        startActivity(intent)
    }

    fun navigateToEditReminder(reminder: Reminder) {
        val intent = Intent(this, ReminderEditorActivity::class.java)
        intent.putExtras(ReminderEditorActivity.createExtras(reminder))
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = RemindersListContentsAdapter(this, this)
    }

    override fun onReminderClick(reminder: Reminder) {
        navigateToEditReminder(reminder)
    }

    override fun onArchiveItemClick() {
        viewModel.toggleArchiveExpansion()
    }
}
