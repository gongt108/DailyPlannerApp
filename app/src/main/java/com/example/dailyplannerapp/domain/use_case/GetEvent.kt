package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.repository.EventRepository

class GetEvent(
    private val repository: EventRepository
) {
    suspend operator fun invoke(id: Int): Event? {
        return repository.getEventById(id)
    }
}