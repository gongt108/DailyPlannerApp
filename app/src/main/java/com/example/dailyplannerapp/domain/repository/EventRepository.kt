package com.example.dailyplannerapp.domain.repository

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.events.EventsEvent
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEvents(): Flow<List<Event>>

    suspend fun getEventById(id: Int): Event?

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)
}