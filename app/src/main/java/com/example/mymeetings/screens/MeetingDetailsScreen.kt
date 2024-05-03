package com.example.mymeetings.screens

import android.annotation.SuppressLint
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

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, onReturn: () -> Unit, modifier: Modifier = Modifier) {

    val meetingVM: MeetingDetailsViewModel = viewModel()
    val meetingState by meetingVM.currentState.collectAsState()

    val titleFieldText = remember { mutableStateOf(meetingState!!.title) }
    val dateFieldText = remember { mutableStateOf(Manager.getDateString(meetingState!!.dateTimeMs)) }
    val timeFieldText = remember { mutableStateOf(Manager.getTimeString(meetingState!!.dateTimeMs)) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = meetingVM.getInitialDateInMs(),
        selectableDates = PresentOrFutureSelectableDates
    )
    val showDatePicker = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = meetingVM.getInitialHour(),
        initialMinute = meetingVM.getInitialMinute(),
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
            text = if (!meetingVM.isNewMeeting) stringResource(id = R.string.meetingdetails_header)
            else stringResource(id = R.string.meetingdetails_newitem_header),
            fontSize = 25.sp
        )
        Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TextField(
                value = titleFieldText.value,
                onValueChange = {
                    titleFieldText.value = it
                    meetingState!!.title = it },
                label = { Text(stringResource(id = R.string.meetingdetails_title_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            Row(modifier = modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = dateFieldText.value,
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
                    value = timeFieldText.value,
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
            PersonCard(personInit = meetingState!!.person, onNavigateToClients = onNavigateToClients, modifier = modifier)
            Button(modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                enabled = meetingVM.isButtonEnabled(),
                onClick = {
                    if (!meetingVM.isNewMeeting) meetingVM.onUpdateMeetingClick(meetingState!!.title, meetingState!!.dateTimeMs)
                    else meetingVM.onAddMeetingClick(meetingState!!.title, meetingState!!.dateTimeMs)
                    onReturn()
                }) {
                Text(text = if (!meetingVM.isNewMeeting) stringResource(id = R.string.meetingdetails_save_button)
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
                        // update date&time in VM
                        meetingVM.setDateTimeInMs(datePickerState.selectedDateMillis!!, timePickerState.hour, timePickerState.minute)

                        // update date in dateField
                        dateFieldText.value = Manager.getDateString(meetingState!!.dateTimeMs)

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
                        // update date&time in VM
                        meetingVM.setDateTimeInMs(datePickerState.selectedDateMillis!!, timePickerState.hour, timePickerState.minute)

                        // update date in dateField
                        timeFieldText.value = Manager.getTimeString(meetingState!!.dateTimeMs)

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