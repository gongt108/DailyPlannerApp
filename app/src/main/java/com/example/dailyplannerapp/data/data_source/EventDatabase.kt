package com.example.dailyplannerapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailyplannerapp.domain.model.Event

@Database(
    entities = [Event::class],
    version = 1
)
abstract class EventDatabase: RoomDatabase() {
    abstract val eventDao: EventDao

    companion object{
        const val DATABASE_NAME = "events_db"
    }
}