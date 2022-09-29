package com.example.dailyplannerapp.events.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailyplannerapp.EventTimeFormatter
import com.example.dailyplannerapp.domain.model.Event

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventItem(
    event: Event,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(color = Color(event.color), shape = RoundedCornerShape(4.dp)),
    ) {

Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.width(300.dp)
) {
    Column {
        Text(
            text = event.name,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${
                event.end.format(
                    EventTimeFormatter
                )
            }",
            style = MaterialTheme.typography.caption
        )
    }
    IconButton(
        onClick = onDeleteClick
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete note",
            tint = MaterialTheme.colors.onSurface
        )
    }
}






        }


}