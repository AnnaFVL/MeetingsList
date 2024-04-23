package com.example.mymeetings.viewmodels

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.Meeting
import java.text.SimpleDateFormat

class MeetingsViewModel(): ViewModel() {

    init {
        Manager.initMeetings()
    }

    fun getMeetings() : List<Meeting> {
        Manager.selectedClient.value = null
        return Manager.getMeetings()
    }

    fun getDateTimeString(dateTimeMs: Long): String {
        val dateTimeCalendar = Calendar.getInstance().apply {
            timeInMillis = dateTimeMs
        }
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val dateString = simpleDateFormat.format(dateTimeCalendar.time).toString()
        val hourString = dateTimeCalendar.get(Calendar.HOUR_OF_DAY).toString()
        val minuteString = dateTimeCalendar.get(Calendar.MINUTE).toString()

        return "$dateString $hourString:$minuteString"
    }

}

