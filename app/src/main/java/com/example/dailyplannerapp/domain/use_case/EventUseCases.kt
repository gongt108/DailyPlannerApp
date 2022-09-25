package com.example.dailyplannerapp.domain.use_case

data class EventUseCases (
    val getEvents: GetEvents,
    val deleteEvent: DeleteEvent,
    val addEvent: AddEvent,
    val getEvent: GetEvent
        )