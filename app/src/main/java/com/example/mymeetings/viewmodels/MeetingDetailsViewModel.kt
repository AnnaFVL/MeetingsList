package com.example.mymeetings.viewmodels

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.Meeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("SimpleDateFormat")
class MeetingDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    var isNewMeeting: Boolean = false
    private val _currentState = MutableStateFlow<Meeting?>(null)
    val currentState: StateFlow<Meeting?> = _currentState

    init {
        val id = stateHandle.get<Int>("meeting_id")
        if (id == -1 || id == null) {
            isNewMeeting = true
            _currentState.value = Meeting(0, "", System.currentTimeMillis(), Manager.emptyClient)
        }
        else {
            isNewMeeting = false
            _currentState.value = Manager.getMeetingById(id)
        }
    }
    fun onUpdateMeetingClick (titleNewValue: String, dateTimeMsNewValue: Long) {
        val id = _currentState.value?.id ?: -1
        if (id > -1) {
            Manager.updateMeeting(id, titleNewValue, dateTimeMsNewValue)
        }
    }

    fun onAddMeetingClick (titleNewValue: String, dateTimeMsNewValue: Long) {
        Manager.addNewMeeting(titleNewValue, dateTimeMsNewValue)
    }

    // Is Add/Save button enabled?
    fun isButtonEnabled() : Boolean {
        val isTitlePresent = (_currentState.value?.title != "")
        val isPersonPresent = (Manager.selectedClient.value != null || !isNewMeeting)
        return isTitlePresent && isPersonPresent
    }

    //////
    // For date / time picker
    //////
    fun getInitialDateInMs(): Long {
        if (isNewMeeting || _currentState.value == null) return System.currentTimeMillis()
        else return _currentState.value!!.dateTimeMs
    }

    fun getInitialHour(): Int {
        if (isNewMeeting || _currentState.value == null) return 0
        else {
            val dateTimeCalendar = Calendar.getInstance(TimeZone.getDefault()).apply {
                timeInMillis = _currentState.value!!.dateTimeMs
            }
            return dateTimeCalendar.get(Calendar.HOUR_OF_DAY)
        }
    }

    fun getInitialMinute(): Int {
        if (isNewMeeting || _currentState.value == null) return 0
        else {
            val dateTimeCalendar = Calendar.getInstance(TimeZone.getDefault()).apply {
                timeInMillis = _currentState.value!!.dateTimeMs
            }
            return dateTimeCalendar.get(Calendar.MINUTE)
        }
    }

    fun setDateTimeInMs(datePickerValue: Long, timeHourPickerValue: Int, timeMinutePickerValue: Int) {
        val selectedDateTimeCalendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            timeInMillis = datePickerValue
        }
        selectedDateTimeCalendar.set(Calendar.HOUR_OF_DAY, timeHourPickerValue)
        selectedDateTimeCalendar.add(Calendar.MINUTE, timeMinutePickerValue)
        _currentState.value?.dateTimeMs = selectedDateTimeCalendar.timeInMillis
    }
}