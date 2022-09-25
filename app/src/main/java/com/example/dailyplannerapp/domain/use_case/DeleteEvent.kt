package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.repository.EventRepository

class DeleteEvent(
    private val repository: EventRepository
) {

    suspend operator fun invoke(event: Event) {
        repository.deleteEvent(event)
    }
}