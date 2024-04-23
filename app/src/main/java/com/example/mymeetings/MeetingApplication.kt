package com.example.mymeetings

import android.app.Application
import android.content.Context

class MeetingApplication: Application() {
    init { app = this }
    companion object {
        private lateinit var app: MeetingApplication
        fun getAppContext(): Context = app.applicationContext
    }
}