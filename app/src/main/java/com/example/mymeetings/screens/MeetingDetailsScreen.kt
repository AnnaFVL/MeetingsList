package com.example.mymeetings.screens

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.mymeetings.data.Name
import com.example.mymeetings.R
import com.example.mymeetings.data.Manager

@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, onReturn: () -> Unit, modifier: Modifier = Modifier) {

    val meetingVM: MeetingDetailsViewModel = viewModel()
    val item = meetingVM.state.value
    val personInit = meetingVM.personAreaInfo.value

    //val meetingTitleInitVal: String = item?.title ?: ""
    //val meetingDateTimeInitVal: String = item?.date ?: ""

    val meetingTitle by meetingVM.title.collectAsState()
    val meetingDateTime by meetingVM.date.collectAsState()

    //val meetingTitle = remember { mutableStateOf(meetingTitleInitVal) }
    //val meetingDateTime = remember { mutableStateOf(meetingDateTimeInitVal) }

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
                onValueChange = { newText -> meetingVM.title.value = newText },//meetingTitle.value = newText},
                label = { Text(stringResource(id = R.string.meetingdetails_title_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = meetingDateTime,
                onValueChange = { newText -> meetingVM.date.value = newText }, // meetingDateTime.value = newText },
                label = { Text(stringResource(id = R.string.meetingdetails_datetime_lable)) },
                singleLine = true,
                modifier = modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )
            PersonCard(personInit = personInit, onNavigateToClients = onNavigateToClients, modifier = modifier)
            Button(modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                onClick = {
                    if (item != null) meetingVM.onUpdateMeetingClick(meetingTitle, meetingDateTime)
                    else meetingVM.onAddMeetingClick(meetingTitle, meetingDateTime)
                    onReturn()
                }) {
                Text(text = if (item != null) stringResource(id = R.string.meetingdetails_save_button)
                else stringResource(id = R.string.meetingdetails_add_button))
            }

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