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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MeetingDetailsScreen(onNavigateToClients: () -> Unit, modifier: Modifier = Modifier) {

    val meetingTitle: String = "Title"
    val meetingDateTime: String = "Date and Time"

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Meetings details",
            fontSize=25.sp
        )
        Column (modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TextField(
                value = meetingTitle,
                onValueChange = {},
                label = { Text("Meeting title")},
                singleLine = true,
                modifier = modifier.padding(vertical = 4.dp).fillMaxWidth()
            )
            TextField(
                value = meetingDateTime,
                onValueChange = {},
                label = { Text("Meeting date and time")},
                singleLine = true,
                modifier = modifier.padding(vertical = 4.dp).fillMaxWidth()
            )

            PersonCard(onNavigateToClients = onNavigateToClients, modifier = modifier)

        }
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
                text = "Person",
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
                Text("Select a person")
            }
        }
    }
}