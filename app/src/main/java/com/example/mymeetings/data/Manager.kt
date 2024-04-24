package com.example.mymeetings.data

import androidx.compose.runtime.mutableStateOf
import com.example.mymeetings.MeetingApplication
import com.example.mymeetings.dal.AppDatabase
import com.example.mymeetings.dal.MeetingRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


//val dummyClient = Client(Name("First Name", "Last Name"), "email", Photo(""))
val dummyDateTimeMs: Long = System.currentTimeMillis()
/*val dummyMeetings = mutableListOf( Meeting(0, "Title0", dummyDateTimeMs, dummyClient),
    Meeting(1, "Title1", dummyDateTimeMs, dummyClient),
    Meeting(2, "Title2", dummyDateTimeMs, dummyClient),
    Meeting(3, "Title3", dummyDateTimeMs, dummyClient),
    Meeting(4, "Title4", dummyDateTimeMs, dummyClient),)*/

object Manager {
    //private val meetings: MutableList<Meeting> = mutableListOf<Meeting>() //= dummyMeetings
    var selectedClient = mutableStateOf<Client?>(null)
    val emptyClient: Client = Client(Name("Empty ", "name"), "Empty email", Photo(""))

    private var meetingDAO = AppDatabase.getDaoInstance(MeetingApplication.getAppContext())

    fun getMeetings(): List<Meeting> {
        return runBlocking<List<Meeting>>(Dispatchers.IO){
            val meetingsFromDB = meetingDAO.getAll();
            val mappedMeetings = meetingsFromDB.map { i -> convertToMeeting(i) }
            return@runBlocking mappedMeetings
        }
    }

    private fun convertToMeeting(itemFromDB: MeetingRecord): Meeting {
        val newMeeting: Meeting = Meeting(
            id = itemFromDB.id,
            title = itemFromDB.title,
            dateTimeMs = itemFromDB.dateTime,
            person = Client(
                name = Name(itemFromDB.personFirstName, itemFromDB.personLastName),
                email = itemFromDB.personEmail,
                photoUrl = Photo(medium = itemFromDB.personPhoto)
            )
        )
        return newMeeting
    }

    private fun convertToMeetingRecord(item: Meeting): MeetingRecord {
        val newMeetingRecord: MeetingRecord = MeetingRecord(
            id = item.id,
            title = item.title,
            dateTime = item.dateTimeMs,
            personFirstName = item.person.name.first,
            personLastName = item.person.name.last,
            personEmail = item.person.email,
            personPhoto = item.person.photoUrl.medium,
        )
        return newMeetingRecord
    }

    fun getMeetingById(id: Int): Meeting? {
        return runBlocking<Meeting?>(Dispatchers.IO){
            val meetingRecord = meetingDAO.getMeeting(id);
            return@runBlocking convertToMeeting(meetingRecord)
        }
    }

    fun addNewMeeting(title: String, dateTimeMs: Long) {
        val id: Int = 0
        val client: Client = selectedClient.value ?: emptyClient
        val newMeeting = Meeting(id, title, dateTimeMs, client)
        selectedClient.value = null

        val newMeetingRecord: MeetingRecord = convertToMeetingRecord(newMeeting)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                meetingDAO.addMeeting(newMeetingRecord)
            }
        }
    }

    fun updateMeeting(id: Int, title: String, dateTimeMs: Long) {
        val clientFromMeeting: Client = getMeetingById(id)?.person ?: emptyClient
        val client: Client = selectedClient.value ?: clientFromMeeting
        val updateMeetingRecord: MeetingRecord = MeetingRecord(
            id,
            title,
            dateTimeMs,
            client.name.first,
            client.name.last,
            client.email,
            client.photoUrl.medium
        )

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                meetingDAO.updateMeeting(updateMeetingRecord)
            }
        }

        selectedClient.value = null
    }
}