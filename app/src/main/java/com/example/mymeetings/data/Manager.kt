package com.example.mymeetings.data

import androidx.compose.runtime.mutableStateOf

val dummyClient = Client(Name("First Name", "Last Name"), "email", "photoUrl")
val dummyMeetings = mutableListOf( Meeting(0, "Title0", "Date0", dummyClient),
    Meeting(1, "Title1", "Date1", dummyClient),
    Meeting(2, "Title2", "Date2", dummyClient),
    Meeting(3, "Title3", "Date3", dummyClient),
    Meeting(4, "Title4", "Date4", dummyClient),)

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

    fun addNewMeeting(title: String, date: String) {
        val id: Int = meetings.size
        val client: Client = selectedClient.value ?: emptyClient
        val newMeeting = Meeting(id, title, date, client)
        meetings.add(newMeeting)
        selectedClient.value = null
    }

    fun updateMeeting(id: Int, title: String, date: String) {
        if (id > -1 && id < meetings.size) {
            meetings[id].title = title
            meetings[id].date = date
            if (selectedClient.value != null) meetings[id].person = selectedClient.value!!
        }
       selectedClient.value = null
    }
}