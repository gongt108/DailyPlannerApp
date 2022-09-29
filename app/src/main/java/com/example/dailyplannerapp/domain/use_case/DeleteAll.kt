package com.example.dailyplannerapp.domain.use_case

import com.example.dailyplannerapp.domain.model.Event
import com.example.dailyplannerapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class DeleteAll(
    private val repository: EventRepository
) {
    suspend operator fun invoke(){
        return repository.deleteAll()
    }
}