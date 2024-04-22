package com.example.mymeetings.data

data class Meeting (
    val id: Int,
    var title: String,
    var dateTimeMs: Long,
    var person: Client
)