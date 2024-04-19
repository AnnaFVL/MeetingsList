package com.example.mymeetings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

data class Meeting (
    val id: Int,
    var title: String,
    var date: String,
    var person: Client
)

val dummyClient = Client(Name("First Name", "Last Name"), "email", "photoUrl")
val dummyMeetings = mutableListOf( Meeting(0, "Title0", "Date0", dummyClient),
    Meeting(1, "Title1", "Date1", dummyClient),
    Meeting(2, "Title2", "Date2", dummyClient),
    Meeting(3, "Title3", "Date3", dummyClient),
    Meeting(4, "Title4", "Date4", dummyClient),)

class MeetingsViewModel(): ViewModel() {
    fun getMeetings() = dummyMeetings

}

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