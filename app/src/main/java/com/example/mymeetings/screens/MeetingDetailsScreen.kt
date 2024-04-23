package com.example.mymeetings.screens

import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymeetings.data.Client
import com.example.mymeetings.viewmodels.MeetingDetailsViewModel
import com.example.mymeetings.R
import com.example.mymeetings.data.Manager
import com.example.mymeetings.data.PresentOrFutureSelectableDates
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, onReturn: () -> Unit, modifier: Modifier = Modifier) {

    val meetingVM: MeetingDetailsViewModel = viewModel()
    val item = meetingVM.state.value
    val personInit = meetingVM.personAreaInfo.value

    val meetingTitle by meetingVM.title.collectAsState()
    val meetingDate by meetingVM.date.collectAsState()
    val meetingTime by meetingVM.time.collectAsState()

    val selectedDateTimeMsInit : Long = (item?.dateTimeMs) ?: System.currentTimeMillis()
    val selectedDateTimeMs = remember { mutableStateOf(selectedDateTimeMsInit) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateTimeMs.value,
        //yearRange = IntRange(2024, 2026),
        selectableDates = PresentOrFutureSelectableDates
    )
    val showDatePicker = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = meetingVM.selectedDate.get(Calendar.HOUR_OF_DAY),
        initialMinute = meetingVM.selectedDate.get(Calendar.MINUTE),
        is24Hour = true
    )
    val showTimePicker = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = if (item != null) stringResource(id = R.string.meetingdetails_header)
            else stringResource(id = R.string.meetingdetails_newitem_header),
            fontSize = 25.sp
        )
        Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TextField(
                value = meetingTitle,
                onValueChange = { newText -> meetingVM.updateTitle(newText)},
                label = { Text(stringResource(id = R.string.meetingdetails_title_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            Row(modifier = modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = meetingDate,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.meetingdetails_date_lable)) },
                    singleLine = true,
                    modifier = modifier.weight(1f)
                )
                Button(
                    onClick = { showDatePicker.value = true },
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Select a date")
                }
            }
            Row(modifier = modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = meetingTime,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.meetingdetails_time_lable)) },
                    singleLine = true,
                    modifier = modifier.weight(1f)
                )
                Button(
                    onClick = { showTimePicker.value = true },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Select a time")
                }
            }
            PersonCard(personInit = personInit, onNavigateToClients = onNavigateToClients, modifier = modifier)
            Button(modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                enabled = meetingVM.isButtonEnabled(),
                onClick = {
                    if (item != null) meetingVM.onUpdateMeetingClick(meetingTitle, selectedDateTimeMs.value)
                    else meetingVM.onAddMeetingClick(meetingTitle, selectedDateTimeMs.value)
                    onReturn()
                }) {
                Text(text = if (item != null) stringResource(id = R.string.meetingdetails_save_button)
                else stringResource(id = R.string.meetingdetails_add_button))
            }
            Button(modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                onClick = {
                    onReturn()
                }) {
                Text(stringResource(id = R.string.meetingdetails_cancel_button))
            }
        }
    }

    // Display date picker component
    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }

                        val selectedDateTimeCalendar = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        selectedDateTimeCalendar.add(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        selectedDateTimeCalendar.add(Calendar.MINUTE, timePickerState.minute)
                        selectedDateTimeMs.value = selectedDateTimeCalendar.timeInMillis

                        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
                        val dateText = simpleDateFormat.format(selectedDate.time).toString()
                        meetingVM.updateDate(dateText)

                        showDatePicker.value = false
                    }
            ) { Text("OK") } },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker.value = false }
            ) { Text("Cancel") } }
        )
        {
            DatePicker(state = datePickerState)
        }
    }

    // Display time picker component
    if (showTimePicker.value) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDateTimeCalendar = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        selectedDateTimeCalendar.add(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        selectedDateTimeCalendar.add(Calendar.MINUTE, timePickerState.minute)
                        selectedDateTimeMs.value = selectedDateTimeCalendar.timeInMillis

                        val selectedTimeValue: String = "${timePickerState.hour}:${timePickerState.minute}"
                        meetingVM.updateTime(selectedTimeValue)

                        showTimePicker.value = false
                    }
            ) { Text("OK") } },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker.value = false }
            ) { Text("Cancel") } }
        )
        {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun PersonCard(personInit: Client, onNavigateToClients: () -> Unit, modifier: Modifier) {

    val personSelected = Manager.selectedClient.value
    val person: Client = personSelected ?: personInit

    val personName: String = person.name.first + " " + person.name.last
    val personEmail: String = person.email

    Card (modifier = modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.meetingdetails_persosonarea_lable),
                modifier = modifier.padding(start = 8.dp, top = 8.dp)
            )
            Row(modifier = modifier.padding(8.dp)) {
                Box(
                    modifier = modifier
                        .size(60.dp)
                        .background(Color.Green)
                ) {
                    if (person.photoUrl.medium == "") {
                        Image(painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                            )
                    }
                    else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(person.photoUrl.medium)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                Column(modifier = modifier.padding(start = 8.dp)) {
                    Text(text = personName)
                    Text(text = personEmail)
                }
            }
            Button(modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
                onClick = { onNavigateToClients() }) {
                Text(stringResource(id = R.string.meetingdetails_selectperson_button))
            }
        }
    }
}