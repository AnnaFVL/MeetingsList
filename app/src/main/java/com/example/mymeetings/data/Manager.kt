package com.example.mymeetings.data

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.runtime.mutableStateOf
import com.example.mymeetings.MeetingApplication
import com.example.mymeetings.dal.AppDatabase
import com.example.mymeetings.dal.MeetingRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

object Manager {
    var selectedClient = mutableStateOf<Client?>(null)
    val emptyClient: Client = Client(Name("Empty ", "name"), "Empty email", Photo(""))

    private var meetingDAO = AppDatabase.getDaoInstance(MeetingApplication.getAppContext())

    //////
    // For MeetingsListScreen
    //////
    fun getMeetings(): List<Meeting> {
        return runBlocking(Dispatchers.IO){
            val meetingsFromDB = meetingDAO.getAll();
            val mappedMeetings = meetingsFromDB.map { i -> convertToMeeting(i) }
            return@runBlocking mappedMeetings
        }
    }

    //////
    // For notifications
    //////
    fun getInOneHourMeetingsText(): String {
        return runBlocking(Dispatchers.IO) {
            val inOneHourMeetings = mutableListOf<Meeting>()
            val oneHourInFuture = System.currentTimeMillis() + 3600000
            val allMeetings = getMeetings()
            // Choose meetings in one hour in future
            allMeetings.forEach {
                if (it.dateTimeMs > System.currentTimeMillis() && it.dateTimeMs <= oneHourInFuture) inOneHourMeetings.add(it)
            }
            // Construct string for notification
            var notificationText: String = ""
            inOneHourMeetings.forEach{
                notificationText += it.person.name.first + " " + it.person.name.last + " at " + getTimeString(it.dateTimeMs) + "\n"
            }
            return@runBlocking notificationText
        }
    }

    //////
    // For converting in ROOM data-structure and back
    //////
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

    //////
    // For MeetingDetailsScreen
    //////
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

    //////
    // For time operations
    //////
    @SuppressLint("SimpleDateFormat")
    fun getTimeString(dateTimeMs: Long): String {
        val dateTimeCalendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            timeInMillis = dateTimeMs
        }
        val hourString = dateTimeCalendar.get(Calendar.HOUR_OF_DAY).toString()
        val minuteString = dateTimeCalendar.get(Calendar.MINUTE).toString()

        return "$hourString:$minuteString"
    }

    fun getDateString(dateTimeMs: Long): String {
        val dateTimeCalendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            timeInMillis = dateTimeMs
        }
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(dateTimeCalendar.time).toString()
    }
}