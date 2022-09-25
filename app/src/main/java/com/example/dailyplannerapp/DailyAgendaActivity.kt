package com.example.dailyplannerapp

import android.annotation.SuppressLint
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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Daily Planner App") },
                        )
                    },
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        // Drawer content
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            AddAgendaDrawer()
                        }
                    },

                    //floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FloatingActionButton (scaffoldState, scope)
                    },
                    content = {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Header()
                            ScheduleSidebar()
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
        start = "9:00:00",
        end = "10:00:00",
    ),
    Event(
        name = "Brunch",
        color = Blue.toArgb(),
        date = LocalDate.now().toString(),
        start = "11:00:00",
        end = "15:00:00",
    ),
    Event(
        name = "Return Library Books",
        color = Pink.toArgb(),
        date = LocalDate.now().toString(),
        start = "15:00:00",
        end = "16:00:00",
    )
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
fun FloatingActionButton(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val mContext = LocalContext.current

    FloatingActionButton(
        onClick = {
            scope.launch {
                scaffoldState.drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
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
fun ScheduleSidebar(
    modifier: Modifier = Modifier,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) }
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) {state.animateScrollTo(100)}

    Column(modifier = modifier.verticalScroll(state)) {
        val startTime = LocalTime.MIN
        repeat(25) {i ->

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

@Composable
fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "September 21, 2022",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(12.dp, 8.dp, 0.dp, 0.dp)
        )
        Text(
            text = "Wednesday",
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
fun AddAgendaDrawer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .height(550.dp)
            .width(450.dp)
    ) {
        OutlinedTextField(
            value = "",
            label = { Text("Agenda Item") },
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            label = { Text("Date") },
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            label = { Text("Start Time") },
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            label = { Text("End Time") },
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            label = { Text("Location (Optional)") },
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(12.dp)
                ) {
            Text(text = "Event Type: ")


            Event.eventColors.forEach {color ->
                val colorInt = color.toArgb()
//                Spacer(modifier = Modifier.size(10.dp))
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
                            color =
                            Color.Transparent,
                            shape = CircleShape
                        )

                )
            }
        }
        Button(enabled = false, onClick = {}) {
            Text(text = "Save")
        }
    }
}


