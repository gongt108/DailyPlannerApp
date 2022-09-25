package com.example.dailyplannerapp.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dailyplannerapp.ui.theme.*
import java.time.LocalDate

@Entity
open class Event @RequiresApi(Build.VERSION_CODES.O) constructor(
    val name: String,
    val color: Int,
    val date: String,
    val start: String,
    val end: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val eventColors = listOf(Purple, Blue, Pink, Green, Yellow)
    }
}

class InvalidEventException(message: String): Exception(message)