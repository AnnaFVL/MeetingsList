package com.example.mymeetings.data

val dummyClient = Client(Name("First Name", "Last Name"), "email", "photoUrl")
val dummyMeetings = mutableListOf( Meeting(0, "Title0", "Date0", dummyClient),
    Meeting(1, "Title1", "Date1", dummyClient),
    Meeting(2, "Title2", "Date2", dummyClient),
    Meeting(3, "Title3", "Date3", dummyClient),
    Meeting(4, "Title4", "Date4", dummyClient),)