package com.example.dailyplannerapp.add_edit_event

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailyplannerapp.add_edit_event.components.TransparentHintTextField
import com.example.dailyplannerapp.add_edit_event.ui.theme.DailyPlannerAppTheme
import com.example.dailyplannerapp.domain.model.Event
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditEventScreen(
    navController: NavController,
    eventColor: Int,
    viewModel: AddEditEventViewModel = hiltViewModel()
) {
    val nameState = viewModel.eventName.value
    val dateState = viewModel.eventDate.value
    val startState = viewModel.eventStart.value
    val endState = viewModel.eventEnd.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditEventViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditEventViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditEventEvent.SaveEvent)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save event")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .height(550.dp)
                .width(450.dp)
        ) {
            TransparentHintTextField(
                text = "",
                hint = nameState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEventEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEventEvent.ChangeNameFocus(it))
                },
                isHintVisible = nameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = "",
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
                text = "",
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
                text = "",
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
            Button(enabled = false, onClick = {}) {
                Text(text = "Save")
            }
        }
    }
}

