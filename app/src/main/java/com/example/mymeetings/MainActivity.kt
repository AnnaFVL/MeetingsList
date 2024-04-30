package com.example.mymeetings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymeetings.screens.ClientsListScreen
import com.example.mymeetings.screens.MeetingDetailsScreen
import com.example.mymeetings.screens.MeetingsListScreen
import com.example.mymeetings.ui.theme.MyMeetingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for meetings that should be reminded of
        val app = this.application as MeetingApplication
        app.enqueueReminderRequest()

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
fun MeetingsApp() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "meetingslist") {
        composable(route = "meetingslist") { MeetingsListScreen (onItemClick = { id -> navController.navigate("meetingsdetails/$id")}) }
        composable(route = "meetingsdetails/{meeting_id}",
            arguments = listOf(navArgument("meeting_id") {type = NavType.IntType})
            ) { MeetingDetailsScreen(onNavigateToClients = { navController.navigate("clientslist") }, onReturn = { navController.popBackStack()}) }
        composable(route = "clientslist") { ClientsListScreen(onReturn = { navController.popBackStack()}) }
    }

}

