package com.example.mymeetings.viewmodels

import android.icu.util.Calendar
import android.os.Message
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.Meeting
import com.example.mymeetings.data.dummyMeetings
import java.text.SimpleDateFormat

class MeetingsViewModel(): ViewModel() {
    fun getMeetings() : List<Meeting> {
        Manager.selectedClient.value = null
        return Manager.getMeetings()
    }

}

