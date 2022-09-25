package com.example.dailyplannerapp.add_edit_event

import androidx.compose.ui.focus.FocusState

sealed class AddEditEventEvent {
    data class EnteredName(val value: String): AddEditEventEvent()
    data class ChangeNameFocus(val focusState: FocusState): AddEditEventEvent()
    data class EnteredDate(val value: String): AddEditEventEvent()
    data class ChangeDateFocus(val focusState: FocusState): AddEditEventEvent()
    data class EnteredStart(val value: String): AddEditEventEvent()
    data class ChangeStartFocus(val focusState: FocusState): AddEditEventEvent()
    data class EnteredEnd(val value: String): AddEditEventEvent()
    data class ChangeEndFocus(val focusState: FocusState): AddEditEventEvent()
    data class ChangeColor(val color: Int): AddEditEventEvent()
    object SaveEvent: AddEditEventEvent()
}