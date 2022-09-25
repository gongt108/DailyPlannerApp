package com.example.dailyplannerapp.di

import android.app.Application
import androidx.room.Room
import com.example.dailyplannerapp.data.data_source.EventDatabase
import com.example.dailyplannerapp.data.repository.EventRepositoryImpl
import com.example.dailyplannerapp.domain.repository.EventRepository
import com.example.dailyplannerapp.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventDatabase(app: Application): EventDatabase {
        return Room.databaseBuilder(
            app,
            EventDatabase::class.java,
            EventDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEventRepository(db: EventDatabase): EventRepository{
        return EventRepositoryImpl(db.eventDao)
    }

    @Provides
    @Singleton
    fun provideEventUseCases(repository: EventRepository): EventUseCases {
        return EventUseCases(
            getEvents = GetEvents(repository),
            deleteEvent = DeleteEvent(repository),
            addEvent = AddEvent(repository),
            getEvent = GetEvent(repository)

        )
    }
}