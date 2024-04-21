package com.example.mymeetings.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Client
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.Name
import com.example.mymeetings.data.Meeting
import com.example.mymeetings.data.dummyMeetings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Meeting?>(null)
    val personAreaInfo = mutableStateOf<Client>(Manager.emptyClient)

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) state.value = null
        else {
            state.value = Manager.getMeetingById(id)
            personAreaInfo.value = state.value?.person ?: Manager.emptyClient

            val titleInit  = state.value?.title ?: ""
            val dateInit = state.value?.date ?: ""

            _title.value = titleInit
            _date.value = dateInit
        }
    }
    fun onUpdateMeetingClick (titleNewValue: String, dateNewValue: String) {
        val id = state.value?.id ?: -1
        if (id > -1) {
            Manager.updateMeeting(id, titleNewValue, dateNewValue)
        }
    }

    fun onAddMeetingClick (titleNewValue: String, dateNewValue: String) {
        Manager.addNewMeeting(titleNewValue, dateNewValue)
    }

    fun updateTitle(newValue: String) {
        _title.value = newValue
    }

    fun updateDate(newValue: String) {
        _date.value = newValue
    }
}