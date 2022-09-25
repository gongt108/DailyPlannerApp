package com.example.dailyplannerapp.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventUseCases: EventUseCases
): ViewModel() {
    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state

    private var recentlyDeletedEvent: Event? = null

    private var getEventsJob: Job? = null

    init {
        getEvents()
    }

    fun onEvent(event: EventsEvent) {
        when(event) {
            is EventsEvent.DeleteEvent -> {
                viewModelScope.launch {
                    eventUseCases.deleteEvent(event.event)
                    recentlyDeletedEvent = event.event
                }
            }
            is EventsEvent.RestoreEvent -> {
                viewModelScope.launch {
                    eventUseCases.addEvent(recentlyDeletedEvent?: return@launch)
                    recentlyDeletedEvent = null
                }
            }
        }
    }

    private fun getEvents(){
        //cancels old coroutine when function gets called
        getEventsJob?.cancel()
        getEventsJob = eventUseCases.getEvents()
            .onEach{ events ->
                _state.value = state.value.copy(
                    events = events
                )
            }
            .launchIn(viewModelScope)
    }
}