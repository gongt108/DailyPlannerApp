package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.model.InvalidEventException
import com.example.dailyplannerapp.domain.repository.EventRepository

class AddEvent(
    private val repository: EventRepository
) {

    @Throws(InvalidEventException::class)
    suspend operator fun invoke(event: Event) {
        if(event.name.isBlank()) {
            throw InvalidEventException("The name of the event cannot be empty.")
        }
        if(event.date.isBlank()) {
            throw InvalidEventException("The date of the event cannot be empty.")
        }
        if(event.start.isBlank()) {
            throw InvalidEventException("The start time of the event cannot be empty.")
        }
        if(event.end.isBlank()) {
            throw InvalidEventException("The end time of the event cannot be empty.")
        }
        repository.insertEvent(event)
    }
}