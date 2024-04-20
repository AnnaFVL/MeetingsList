package com.example.mymeetings.data

data class Meeting (
    val id: Int,
    var title: String,
    var date: String,
    var person: Client
)