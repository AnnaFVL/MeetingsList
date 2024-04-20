package com.example.mymeetings.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.dummyMeetings

class MeetingsViewModel(): ViewModel() {
    fun getMeetings() = dummyMeetings

}

