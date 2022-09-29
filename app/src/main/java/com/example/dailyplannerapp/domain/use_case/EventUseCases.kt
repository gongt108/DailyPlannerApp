package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.repository.EventRepository

data class EventUseCases (
    val getEvents: GetEvents,
    val deleteEvent: DeleteEvent,
    val addEvent: AddEvent,
    val getEvent: GetEvent,
    val deleteAll: DeleteAll
)