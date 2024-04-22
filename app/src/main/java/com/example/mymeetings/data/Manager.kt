package com.example.mymeetings.data

import androidx.compose.runtime.mutableStateOf
import java.time.LocalDateTime

val dummyClient = Client(Name("First Name", "Last Name"), "email", "photoUrl")
val dummyDateTimeMs : Long = System.currentTimeMillis()
val dummyMeetings = mutableListOf( Meeting(0, "Title0", dummyDateTimeMs, dummyClient),
    Meeting(1, "Title1", dummyDateTimeMs, dummyClient),
    Meeting(2, "Title2", dummyDateTimeMs, dummyClient),
    Meeting(3, "Title3", dummyDateTimeMs, dummyClient),
    Meeting(4, "Title4", dummyDateTimeMs, dummyClient),)

object Manager {
    private val meetings = dummyMeetings
    var selectedClient = mutableStateOf<Client?>(null)
    val emptyClient: Client = Client(Name("Empty ", "name"), "Empty email", "Empty url")
    fun getMeetings() : List<Meeting> {
        return meetings
    }

    fun getMeetingById(id: Int) : Meeting? {
        if (id > -1 && id < meetings.size) {
            return meetings[id]
        }
        else
            return null
    }

    fun addNewMeeting(title: String, dateTimeMs: Long) {
        val id: Int = meetings.size
        val client: Client = selectedClient.value ?: emptyClient
        val newMeeting = Meeting(id, title, dateTimeMs, client)
        meetings.add(newMeeting)
        selectedClient.value = null
    }

    fun updateMeeting(id: Int, title: String, dateTimeMs: Long) {
        if (id > -1 && id < meetings.size) {
            meetings[id].title = title
            meetings[id].dateTimeMs = dateTimeMs
            if (selectedClient.value != null) meetings[id].person = selectedClient.value!!
        }
       selectedClient.value = null
    }
}