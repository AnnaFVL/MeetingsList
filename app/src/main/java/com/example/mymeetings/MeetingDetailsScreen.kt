package com.example.mymeetings

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, modifier: Modifier = Modifier) {

    val meetingVM: MeetingDetailsViewModel = viewModel()
    val item = meetingVM.state.value

    //val meetingTitle: String = "Title"
    //val meetingDateTime: String = "Date and Time"

    if (item != null) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.meetingdetails_header),
                fontSize = 25.sp
            )
            Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                TextField(
                    value = item.title,
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.meetingdetails_title_lable)) },
                    singleLine = true,
                    modifier = modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )
                TextField(
                    value = item.date,
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.meetingdetails_datetime_lable)) },
                    singleLine = true,
                    modifier = modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )

                PersonCard(onNavigateToClients = onNavigateToClients, modifier = modifier)

            }
        }
    } else {
        Text("Item is null")
    }
}

@Composable
fun PersonCard(onNavigateToClients: () -> Unit, modifier: Modifier) {
    val personName: String = "Person name"
    val personEmail: String = "Person email"

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
            Button(modifier = modifier.align(Alignment.CenterHorizontally),
                onClick = { onNavigateToClients() }) {
                Text(stringResource(id = R.string.meetingdetails_selectperson_button))
            }
        }
    }
}