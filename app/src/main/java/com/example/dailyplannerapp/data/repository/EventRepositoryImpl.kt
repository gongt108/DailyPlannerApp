package com.example.dailyplannerapp.data.repository

import com.example.dailyplannerapp.data.data_source.EventDao
import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val dao: EventDao
): EventRepository {

    override fun getEvents(): Flow<List<Event>> {
        return dao.getEvents()
    }

    override suspend fun getEventById(id: Int): Event? {
        return dao.getEventById(id)
    }

    override suspend fun insertEvent(event: Event) {
        dao.insertEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        dao.deleteEvent(event)
    }
}