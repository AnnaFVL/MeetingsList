package com.example.mymeetings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items

@Composable
fun MeetingsListScreen(onNavigateToDetails: () -> Unit, modifier: Modifier = Modifier) {

    val meetingsVM: MeetingsViewModel = viewModel()

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Meetings List",
            fontSize=25.sp
        )
        LazyColumn (modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
        ) {
            items(meetingsVM.getMeetings()) { meeting ->
                MeetingItem(meeting)
            }
        }
        Button(onClick = { onNavigateToDetails() }) {
            Text("Go to Meeting details")
        }
    }

}

@Composable
fun MeetingItem(item: Meeting) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
    ) {
        Column {
            Text(item.title)
            Text(item.date)
            Text(item.person)
        }
    }
}