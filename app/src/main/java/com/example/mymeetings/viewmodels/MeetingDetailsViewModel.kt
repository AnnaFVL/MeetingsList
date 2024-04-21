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

class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Meeting?>(null)
    val personAreaInfo = mutableStateOf<Client>(Manager.emptyClient)

    private var titleInit : String = ""
    private var dateInit : String = ""

    //val meetingTitleInitVal: String = item?.title ?: ""
    //val meetingDateTimeInitVal: String = item?.date ?: ""

    val title = MutableStateFlow(titleInit) //mutableStateOf<String>(titleInit)
    val date = MutableStateFlow(dateInit)// mutableStateOf<String>(dateInit)

    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) state.value = null
        else {
            state.value = Manager.getMeetingById(id)
            personAreaInfo.value = state.value?.person ?: Manager.emptyClient

            titleInit  = state.value?.title ?: ""
            dateInit = state.value?.date ?: ""

            title.value = titleInit
            date.value = dateInit
        }
        //if (Manager.selectedClient.value!= null) personAreaInfo.value = Manager.selectedClient.value!!
    }
    fun onUpdateMeetingClick (titleNewValue: String, dateNewValue: String) {
        val id = state.value?.id ?: -1
        if (id > -1) {
            Manager.updateMeeting(id, titleNewValue, dateNewValue)
        }
        Manager.selectedClient.value = null
    }

    fun onAddMeetingClick (titleNewValue: String, dateNewValue: String) {
        Manager.addNewMeeting(titleNewValue, dateNewValue)
        Manager.selectedClient.value = null
    }
}