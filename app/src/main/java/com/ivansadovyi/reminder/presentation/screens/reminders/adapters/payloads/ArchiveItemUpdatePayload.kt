package com.ivansadovyi.reminder.presentation.screens.reminders.adapters.payloads

sealed class ArchiveItemUpdatePayload {

	object Expand : ArchiveItemUpdatePayload()

	object Collapse : ArchiveItemUpdatePayload()
}