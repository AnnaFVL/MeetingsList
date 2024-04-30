package com.example.mymeetings.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mymeetings.data.Manager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        //makeReminderNotification("This is my reminder", applicationContext)


        return withContext(Dispatchers.IO) {
            return@withContext try {
                //delay(100000)

                val meetingPersonText = Manager.getInOneHourMeetingsText()
                if (meetingPersonText != "") makeReminderNotification(meetingPersonText, applicationContext)


                Result.success()
            } catch (throwable: Throwable) {
                Log.e("In ReminderWorker", "In catch block", throwable )

                Result.failure()
            }
        }


    }
}
