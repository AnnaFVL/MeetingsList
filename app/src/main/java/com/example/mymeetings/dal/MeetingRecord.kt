package com.example.mymeetings.dal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingRecord(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="date_time")
    val dateTime: Long,
    @ColumnInfo(name="first_name")
    val personFirstName: String,
    @ColumnInfo(name="last_name")
    val personLastName: String,
    @ColumnInfo(name="email")
    val personEmail: String,
    @ColumnInfo(name="photo")
    val personPhoto: String
)