package com.example.mymeetings.dal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.coroutines.CoroutineContext

@Database(entities = [MeetingRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: MeetingDAO
    //abstract fun meetingDao(): MeetingDAO

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

    /*companion object {
        @Volatile
        private var instance: AppDatabase?= null
        private val Lock = Any()

        fun get(): AppDatabase {
            return instance!!
        }
        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(Lock) {
                instance ?: BuildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun BuildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"meeting-db").build()
    }*/
}
