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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color

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
                MeetingItem(meeting, modifier)
            }
        }
        FloatingActionButton(modifier = modifier
            .padding(horizontal = 16.dp)
            .align(Alignment.End),
            onClick = { onNavigateToDetails() }) {
            Icon(Icons.Filled.Add, contentDescription = "Add new meeting")
        }
    }

}

@Composable
fun MeetingItem(item: Meeting, modifier: Modifier) {
    Card (modifier = modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = modifier.padding(8.dp)) {
            Box(modifier = modifier
                .size(60.dp)
                .background(Color.Green)
            )
            Column (modifier = modifier.padding(start = 8.dp)) {
                Text(item.title)
                Text(item.date)
                Text(item.person)
            }
        }

    }
}