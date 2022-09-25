package com.example.dailyplannerapp.events

import com.example.dailyplannerapp.domain.model.Event

data class EventsState(
    val events: List<Event> = emptyList()
    )