package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEvents(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
    }
}