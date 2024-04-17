package com.example.mymeetings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Meeting (
    val id: Int,
    val title: String,
    val date: String,
    val person: String
)

val dummyMeetings = listOf( Meeting(0, "Title0", "Date0", "Person0"),
    Meeting(1, "Title1", "Date1", "Person1"),
    Meeting(2, "Title2", "Date2", "Person2"),
    Meeting(3, "Title3", "Date3", "Person3"),
    Meeting(4, "Title4", "Date4", "Person4"),)

class MeetingsViewModel(): ViewModel() {
    fun getMeetings() = dummyMeetings

}

class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Meeting?>(null)

    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) state.value = null
        else state.value = dummyMeetings[id]

        //if (id != null) state.value = dummyMeetings[id] else dummyMeetings[0]
    }
}