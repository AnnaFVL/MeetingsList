package com.example.mymeetings.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymeetings.data.Meeting
import com.example.mymeetings.viewmodels.MeetingsViewModel
import com.example.mymeetings.R

@Composable
fun MeetingsListScreen(modifier: Modifier = Modifier, onItemClick: (id: Int) -> Unit = {}) {

    val meetingsVM: MeetingsViewModel = viewModel()
    var dateTimeString : String = ""

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.meetingslist_header),
            fontSize=25.sp
        )
        LazyColumn (modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(650.dp)
        ) {
            items(meetingsVM.getMeetings()) { meeting ->
                dateTimeString = meetingsVM.getDateTimeString(meeting.dateTimeMs)
                MeetingItem(meeting, dateTimeString, modifier, onItemClick = { id -> onItemClick(id) })
            }
        }
        FloatingActionButton(modifier = modifier
            .padding(horizontal = 16.dp)
            .align(Alignment.End),
            onClick = { onItemClick(-1) } ) {
            Icon(Icons.Filled.Add, contentDescription = "Add new meeting")
        }
    }
}

@Composable
fun MeetingItem(item: Meeting, dateTimeString: String, modifier: Modifier, onItemClick: (id: Int) -> Unit) {
    Card (modifier = modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()
        .clickable { onItemClick(item.id) }
    ) {
        Row(modifier = modifier.padding(8.dp)) {
            Box(modifier = modifier
                .size(60.dp)
                .background(Color.Green)
            ) {
                if (item.person.photoUrl.medium == "") {
                    Image(painter = painterResource(R.drawable.baseline_person_24),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
                else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.person.photoUrl.medium)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            Column (modifier = modifier.padding(start = 8.dp)) {
                Text(item.title)
                Text(dateTimeString)
                Text("${item.person.name.first} ${item.person.name.last}")
            }
        }
    }
}