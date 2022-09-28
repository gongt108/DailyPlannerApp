package com.example.dailyplannerapp.add_edit_event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.model.InvalidEventException
import com.example.dailyplannerapp.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _eventName = mutableStateOf(EventTextFieldState(
        text = "Grocery Shopping"
    ))
    val eventName: State<EventTextFieldState> = _eventName

    @RequiresApi(Build.VERSION_CODES.O)
    private val _eventDate = mutableStateOf(EventTextFieldState(
        text = LocalDate.now().toString()
    ))
    @RequiresApi(Build.VERSION_CODES.O)
    val eventDate : State<EventTextFieldState> = _eventDate

    private val _eventStart = mutableStateOf(EventTextFieldState(
        text = "00:00"
    ))
    val eventStart : State<EventTextFieldState> = _eventStart

    private val _eventEnd = mutableStateOf(EventTextFieldState(
        text = "23:59"
    ))
    val eventEnd : State<EventTextFieldState> = _eventEnd

    private val _eventColor = mutableStateOf(Event.eventColors[0].toArgb())
    val eventColor: State<Int> = _eventColor

    //the event in this variable is not related to the previous event variables. refers to kotlin events
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentEventId : Int? = null

    init {
        savedStateHandle.get<Int>("eventId")?.let {eventId ->
            if (eventId != -1) {
                viewModelScope.launch {
                    eventUseCases.getEvent(eventId)?.also{event ->
                        currentEventId = event.id
                        _eventName.value = eventName.value.copy(
                            text = event.name,
                            isHintVisible = false
                        )
                        _eventDate.value = eventDate.value.copy(
                            text = event.date,
                            isHintVisible = false
                        )
                        _eventStart.value = eventStart.value.copy(
                            text = event.start,
                            isHintVisible = false
                        )
                        _eventEnd.value = eventEnd.value.copy(
                            text = event.end,
                            isHintVisible = false
                        )
                        _eventColor.value = event.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEventEvent) {
        when (event) {
            is AddEditEventEvent.EnteredName -> {
                _eventName.value = eventName.value.copy(
                    text = event.value,
                )
            }
            is AddEditEventEvent.ChangeNameFocus -> {
                _eventName.value = eventName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            eventName.value.text.isBlank()
                )
            }

            is AddEditEventEvent.EnteredDate -> {
                _eventDate.value = eventDate.value.copy(
                    text = event.value,
                )
            }
            is AddEditEventEvent.ChangeDateFocus -> {
                _eventDate.value = eventDate.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            eventDate.value.text.isBlank()
                )
            }

            is AddEditEventEvent.EnteredStart -> {
                _eventStart.value = eventStart.value.copy(
                    text = event.value,
                )
            }
            is AddEditEventEvent.ChangeStartFocus -> {
                _eventStart.value = eventStart.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            eventStart.value.text.isBlank()
                )
            }

            is AddEditEventEvent.EnteredEnd -> {
                _eventEnd.value = eventEnd.value.copy(
                    text = event.value,
                )
            }
            is AddEditEventEvent.ChangeEndFocus -> {
                _eventEnd.value = eventEnd.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            eventEnd.value.text.isBlank()
                )
            }

            is AddEditEventEvent.ChangeColor -> {
                _eventColor.value = event.color
            }

            is AddEditEventEvent.SaveEvent -> {
                viewModelScope.launch {
                    try {
                        eventUseCases.addEvent(
                            Event(
                                name = eventName.value.text,
                                date = eventDate.value.text,
                                start = eventStart.value.text,
                                end = eventEnd.value.text,
                                color = eventColor.value,
                                id = currentEventId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: InvalidEventException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save event."
                            )
                        )
                    }
                }
            }

        }

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}