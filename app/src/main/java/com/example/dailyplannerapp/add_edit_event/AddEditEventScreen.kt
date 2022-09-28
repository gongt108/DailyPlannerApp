package com.example.dailyplannerapp.add_edit_event

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailyplannerapp.add_edit_event.components.TransparentHintTextField
import com.example.dailyplannerapp.domain.model.Event

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditEventScreen(
    event: Event,
    viewModel: AddEditEventViewModel = hiltViewModel(),
) {

        val nameState = viewModel.eventName.value
        val dateState = viewModel.eventDate.value
        val startState = viewModel.eventStart.value
        val endState = viewModel.eventEnd.value

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .height(450.dp)
                .width(450.dp)
        ) {
            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEventEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEventEvent.ChangeNameFocus(it))
                },
                isHintVisible = nameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = dateState.text,
                hint = dateState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEventEvent.EnteredDate(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEventEvent.ChangeDateFocus(it))
                },
                isHintVisible = dateState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = startState.text,
                hint = startState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEventEvent.EnteredStart(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEventEvent.ChangeStartFocus(it))
                },
                isHintVisible = false,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = endState.text,
                hint = endState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEventEvent.EnteredEnd(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEventEvent.ChangeEndFocus(it))
                },
                isHintVisible = false,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(12.dp)
            ) {
                Text(text = "Event Type: ")


                Event.eventColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(
                                color = Color(colorInt),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = if (viewModel.eventColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                viewModel.onEvent(AddEditEventEvent.ChangeColor(colorInt))
                            }

                    )
                }
            }

        }


}

