package com.example.mymeetings

import androidx.lifecycle.ViewModel

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