package com.example.mymeetings.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymeetings.data.Client
import com.example.mymeetings.viewmodels.ClientsViewModel
import com.example.mymeetings.R

@Composable
fun ClientsListScreen(onReturn: () -> Unit, modifier: Modifier = Modifier) {

    val viewModel: ClientsViewModel = viewModel()
    viewModel.getClients()

    Column(
        modifier = modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.clientslist_header),
            fontSize = 25.sp
        )
        LazyColumn(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .weight(1f)
        ) {
            items(viewModel.state.value) { client ->
                ClientItem(client, onItemClick = {
                    viewModel.selectClient(client)
                    onReturn()
                })
            }
        }
        Button(modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
            onClick = {
                onReturn()
            }) {
            Text(stringResource(id = R.string.clientslist_back_button))
        }
    }
}

@Composable
fun ClientItem(item: Client, onItemClick: (selected: Client) -> Unit){
    Card (modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()
        .clickable { onItemClick(item) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Green)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.photoUrl.medium)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = "${item.name.first} ${item.name.last}")
                Text(text = item.email)
            }
        }
    }
}