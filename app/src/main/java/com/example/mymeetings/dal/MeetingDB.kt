package com.example.mymeetings.dal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MeetingRecord::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: MeetingDAO

    companion object {
        @Volatile
        private var INSTANCE: MeetingDAO? = null
        fun getDaoInstance(context: Context): MeetingDAO
        {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context):
                AppDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "meeting-db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
