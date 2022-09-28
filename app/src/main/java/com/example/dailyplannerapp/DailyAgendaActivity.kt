package com.example.dailyplannerapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailyplannerapp.add_edit_event.AddEditEventEvent
import com.example.dailyplannerapp.add_edit_event.AddEditEventScreen
import com.example.dailyplannerapp.add_edit_event.AddEditEventViewModel
import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.events.EventsEvent
import com.example.dailyplannerapp.events.EventsViewModel
import com.example.dailyplannerapp.events.components.EventItem
import com.example.dailyplannerapp.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class DailyAgendaActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyPlannerAppTheme {
                // A surface container using the 'background' color from the theme

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val dialogState: MutableState<Boolean> = remember {
                    mutableStateOf(false)
                }

                val viewModel: EventsViewModel = hiltViewModel()
                val savedEvents = viewModel.state.value

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Daily Planner App") },
                        )
                    },
                    scaffoldState = scaffoldState,

                            //
                    //                    drawerContent = {
                    //                        // Drawer content
                    //                        Column(
                    //                            modifier = Modifier.fillMaxSize(),
                    //                            verticalArrangement = Arrangement.Center
                    //                        ) {
                    //                            AddEditEventScreen(
                    //                                navController = navController,
                    //                            )
                    //                        }
                    //                    },

                            //floatingActionButtonPosition = FabPosition.End,
                            floatingActionButton = {
                        FloatingActionButton (scaffoldState, scope, dialogState)
                    },
                    content = {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AddEditEventDialog(sampleEvent, dialogState)
                            Header()
                            NavigationBar()
                            ScheduleBackground(dialogState, viewModel)
                        }

                    }
                )

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val EventTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

@RequiresApi(Build.VERSION_CODES.O)
private val sampleEvents = listOf(
    Event(
        name = "Grocery Shopping",
        color = Purple.toArgb(),
        date = LocalDate.now().toString(),
        start = "09:00:00",
        end = "10:00:00",
    ),
//    Event(
//        name = "Brunch",
//        color = Blue.toArgb(),
//        date = LocalDate.now().toString(),
//        start = "11:00:00",
//        end = "15:00:00",
//    ),
//    Event(
//        name = "Return Library Books",
//        color = Pink.toArgb(),
//        date = LocalDate.now().toString(),
//        start = "15:00:00",
//        end = "16:00:00",
//    )
)

@RequiresApi(Build.VERSION_CODES.O)
private val sampleEvent = Event(
    name = "Grocery Shopping",
    color = Purple.toArgb(),
    date = LocalDate.now().toString(),
    start = "09:00:00",
    end = "10:00:00",
)


// Sidebar
@RequiresApi(Build.VERSION_CODES.O)
private val HourFormatter = DateTimeFormatter.ofPattern("h a")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier
) {
    Text(
        text = time.format(HourFormatter),
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
    )
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun BasicSidebarLabelPreview() {
//    DailyPlannerAppTheme {
//        BasicSidebarLabel(time = LocalTime.NOON, Modifier.sizeIn(maxHeight = 64.dp))
//    }
//}

private fun mToast(context: Context){
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}

@Composable
fun FloatingActionButton(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    dialogState: MutableState<Boolean>
) {
    val mContext = LocalContext.current

    FloatingActionButton(
        onClick = {
            dialogState.value = true
        },
        modifier= Modifier.size(50.dp),
        shape = CircleShape,
        backgroundColor =  colorResource(R.color.teal_200),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = null, tint = Color.White)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddAgendaButtonPreview() {
//    DailyPlannerAppTheme {
//        AddAgendaItem()
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleBackground(
    dialogState: MutableState<Boolean>,
    viewModel: EventsViewModel,
    modifier: Modifier = Modifier,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) }
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) {state.animateScrollTo(100)}

    Column(modifier = modifier.verticalScroll(state)) {
        val startTime = LocalTime.MIN
        Box {
            Schedule(viewModel, dialogState)
            Column {
                repeat(25) { i ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(50.dp)
                            .padding(16.dp, 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .width(50.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            label(
                                startTime.plusHours(i.toLong()),
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                        ) {
                            val canvasWidth = size.width
                            val canvasHeight = size.height

                            drawLine(
                                start = Offset(x = 0f, y = canvasHeight / 2),
                                end = Offset(x = canvasWidth, y = canvasHeight / 2),
                                color = Color.LightGray,
                                strokeWidth = 3f
                            )
                        }

                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val today = LocalDateTime.now()
        val month = today.month.toString()
        val day = today.dayOfMonth.toString()
        val year = today.year.toString()

        Text(
            text = "$month ${day}, $year",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(12.dp, 8.dp, 0.dp, 0.dp)
        )
        Text(
            text = LocalDateTime.now().dayOfWeek.toString(),
            modifier = Modifier
                .padding(12.dp, 0.dp, 0.dp, 4.dp)
        )
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 0.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                start = Offset(x = 0f, y = canvasHeight / 2),
                end = Offset(x = canvasWidth, y = canvasHeight / 2),
                color = Color.DarkGray,
                strokeWidth = 3f
            )
        }
    }
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier
){
    val activity = (LocalContext.current as? Activity)
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { activity?.finish() }
        ) {
            Text(text = "Go to Month View")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Schedule(
    viewModel: EventsViewModel,
    dialogState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val events = viewModel.state.value.events
    val hourHeight = 50
//    Column(
//        modifier = Modifier
//            .padding(80.dp, 26.dp, 16.dp, 16.dp)
//    ){
        Box (
            modifier = Modifier
                .padding(80.dp, 26.dp, 16.dp, 16.dp)
        ){
            events.sortedBy(Event::start).forEach { event ->
                val dateStart = LocalDateTime.parse("${event.date}T00:00:00")
                val startTime = LocalDateTime.parse("${event.date}T${event.start}")
                val endTime = LocalDateTime.parse("${event.date}T${event.end}")
                val duration = ChronoUnit.HOURS.between(startTime, endTime)
                val eventHeight = (hourHeight * duration).toFloat()
                val startHour = ChronoUnit.HOURS.between(dateStart, startTime)
                val paddingHeight = (startHour * hourHeight).toFloat()

                EventItem(
                    event = event,
                    modifier = Modifier
                        .height(eventHeight.dp)
                        .offset(y = paddingHeight.dp),
                    onDeleteClick = {
                        viewModel.onEvent(EventsEvent.DeleteEvent(event))

                    }

                )
            }


//        }

    }

}

//@RequiresApi(Build.VERSION_CODES.O)
//fun editEvent(
//    event: Event,
//    dialogState: MutableState<Boolean>
//) {
//    AddEditEventDialog(event = event, dialogState = dialogState)
//    dialogState.value = true
//
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditEventDialog(
    event: Event,
    dialogState: MutableState<Boolean>,
    viewModel: AddEditEventViewModel = hiltViewModel(),
    ) {
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                Card(
                    modifier = Modifier.height(600.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .width(450.dp)
                    ) {
                        AddEditEventScreen(event)
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                enabled = true,
                                onClick = {
                                    viewModel.onEvent(AddEditEventEvent.SaveEvent)
                                    dialogState.value = false
                                }
                            ) {
                                Text(text = "Save Event")
                            }

                        }
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        )
    }
}

