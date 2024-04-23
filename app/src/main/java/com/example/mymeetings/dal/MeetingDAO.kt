package com.example.mymeetings.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MeetingDAO {
    @Query("SELECT * FROM meetings")
    suspend fun getAll(): List<MeetingRecord>

    @Query("SELECT * FROM meetings WHERE id=:id")
    suspend fun getMeeting(id: Int): MeetingRecord

    @Insert
    suspend fun addMeeting(item: MeetingRecord)

    @Update
    suspend fun updateMeeting(item: MeetingRecord)
}