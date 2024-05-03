package com.example.mymeetings

import android.app.Application
import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mymeetings.workers.ReminderWorker
import java.time.Duration

class MeetingApplication: Application() {

    private lateinit var workManager: WorkManager

    init {
        app = this
    }
    companion object {
        private lateinit var app: MeetingApplication
        fun getAppContext(): Context = app.applicationContext
    }

    // Schedule periodic (1 time / 15 min) worker to remind about meetings that are planned in next 1 hour
    fun enqueueReminderRequest() {
        workManager = WorkManager.getInstance(applicationContext)

        // Actually worker ignore duration <15 min
        val requestBuilder = PeriodicWorkRequestBuilder<ReminderWorker>(Duration.ofMinutes(15))
        workManager.enqueue(requestBuilder.build())
    }
}