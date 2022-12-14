package com.example.dailyplannerapp.events

import com.example.dailyplannerapp.domain.model.Event

sealed class EventsEvent {

    object DeleteAll: EventsEvent()
    data class DeleteEvent(val event: Event): EventsEvent()
    object RestoreEvent: EventsEvent()


}