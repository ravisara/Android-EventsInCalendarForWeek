package com.example.eventsincalendarforweek.repository

import android.app.Application
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class EventsDataRepository private constructor(application: Application){

    companion object : SingletonHolder<EventsDataRepository, Application>(::EventsDataRepository)

    // TODO propagate any errors to the UI layer
    suspend fun getCalendarEventsInSpecifiedInterval(startDateTime: String, endDateTime: String): Response<CalendarEvents> {
        return RetrofitInstance.api.getCalendarEventsInSpecifiedInterval(startDateTime, endDateTime)
    }

}

