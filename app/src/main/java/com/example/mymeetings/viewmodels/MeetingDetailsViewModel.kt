package com.example.mymeetings.viewmodels

import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Client
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.Meeting
import com.example.mymeetings.data.dummyDateTimeMs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat

class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Meeting?>(null)
    val personAreaInfo = mutableStateOf<Client>(Manager.emptyClient)
    var selectedDate: Calendar = Calendar.getInstance().apply { timeInMillis = dummyDateTimeMs }

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _time = MutableStateFlow("")
    val time: StateFlow<String> = _time

    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) state.value = null
        else {
            state.value = Manager.getMeetingById(id)
            personAreaInfo.value = state.value?.person ?: Manager.emptyClient

            val titleInit  = state.value?.title ?: ""
            selectedDate = Calendar.getInstance().apply { timeInMillis = state.value!!.dateTimeMs }

            _title.value = titleInit

            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            _date.value = simpleDateFormat.format(selectedDate.time).toString()

            _time.value = "${selectedDate.get(Calendar.HOUR_OF_DAY)}:${selectedDate.get(Calendar.MINUTE)}"

        }
    }
    fun onUpdateMeetingClick (titleNewValue: String, dateTimeMsNewValue: Long) {
        val id = state.value?.id ?: -1
        if (id > -1) {
            Manager.updateMeeting(id, titleNewValue, dateTimeMsNewValue)
        }
    }

    fun onAddMeetingClick (titleNewValue: String, dateTimeMsNewValue: Long) {
        Manager.addNewMeeting(titleNewValue, dateTimeMsNewValue)
    }

    // Is Add/Save button enabled?
    fun isButtonEnabled() : Boolean {
        val isTitlePresent = (_title.value != "")
        val isDatePresent = (_date.value != "")
        val isTimePresent = (_time.value != "")
        val isPersonPresent = (Manager.selectedClient.value != null || state.value != null)
        return isTitlePresent && isDatePresent && isTimePresent && isPersonPresent
    }

    fun updateTitle(newValue: String) {
        _title.value = newValue
    }

    fun updateDate(newValue: String) {
        _date.value = newValue
    }

    fun updateTime(newValue: String) {
        _time.value = newValue
    }
}