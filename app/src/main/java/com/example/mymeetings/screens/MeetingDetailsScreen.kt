package com.example.mymeetings.screens

import android.app.TimePickerDialog
import android.icu.util.Calendar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymeetings.data.Client
import com.example.mymeetings.viewmodels.MeetingDetailsViewModel
import com.example.mymeetings.R
import com.example.mymeetings.data.Manager
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, onReturn: () -> Unit, modifier: Modifier = Modifier) {

    val meetingVM: MeetingDetailsViewModel = viewModel()
    val item = meetingVM.state.value
    val personInit = meetingVM.personAreaInfo.value

    val meetingTitle by meetingVM.title.collectAsState()
    val meetingDate by meetingVM.date.collectAsState()
    val meetingTime = remember { mutableStateOf("Meeting Time") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        yearRange = IntRange(2024, 2026)
    )
    val showDatePicker = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
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
            TextField(
                value = meetingDate,
                onValueChange = { newText -> meetingVM.updateDate(newText)},
                label = { Text(stringResource(id = R.string.meetingdetails_date_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = { showDatePicker.value = true },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Select a date")
            }
            TextField(
                value = meetingTime.value,
                onValueChange = { newText -> meetingTime.value = newText },
                label = { Text(stringResource(id = R.string.meetingdetails_time_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = { showTimePicker.value = true },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Select a time")
            }
            PersonCard(personInit = personInit, onNavigateToClients = onNavigateToClients, modifier = modifier)
            Button(modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                onClick = {
                    if (item != null) meetingVM.onUpdateMeetingClick(meetingTitle, meetingDate)
                    else meetingVM.onAddMeetingClick(meetingTitle, meetingDate)
                    onReturn()
                }) {
                Text(text = if (item != null) stringResource(id = R.string.meetingdetails_save_button)
                else stringResource(id = R.string.meetingdetails_add_button))
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
                        val selectedTimeValue: String = "${timePickerState.hour}:${timePickerState.minute}"
                        meetingTime.value = selectedTimeValue
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
                )
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