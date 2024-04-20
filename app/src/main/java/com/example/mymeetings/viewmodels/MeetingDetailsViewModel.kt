package com.example.mymeetings.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Client
import com.example.mymeetings.data.Name
import com.example.mymeetings.data.Meeting
import com.example.mymeetings.data.dummyMeetings

class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Meeting?>(null)
    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) state.value = null
        else state.value = dummyMeetings[id]
    }
    fun onUpdateMeetingClick (titleNewValue: String, dateNewValue: String) {
        val id = state.value?.id ?: -1
        if (id > -1) {
            dummyMeetings[id].title = titleNewValue
            dummyMeetings[id].date = dateNewValue
        }
    }

    fun onAddMeetingClick (titleNewValue: String, dateNewValue: String) {
        val id: Int = dummyMeetings.size ?: 0
        val newMeeting = Meeting(id, titleNewValue, dateNewValue, Client(Name("New first", "New last"), "New email", "New url"))
        dummyMeetings.add(newMeeting)
    }
}