package com.example.mymeetings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymeetings.ui.theme.MyMeetingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMeetingsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MeetingsApp()
                }
            }
        }
    }
}

@Composable
fun MeetingsApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "meetingslist") {
        composable(route = "meetingslist") { MeetingsListScreen (onNavigateToDetails = { navController.navigate("meetingsdetails") }) }
        composable(route = "meetingsdetails") { MeetingDetailsScreen(onNavigateToClients = { navController.navigate("clientslist") }) }
        composable(route = "clientslist") { ClientsListScreen() }
    }

}