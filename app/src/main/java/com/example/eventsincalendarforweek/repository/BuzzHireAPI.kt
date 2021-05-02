package com.example.eventsincalendarforweek.repository

import retrofit2.Response
import retrofit2.http.GET

interface BuzzHireAPI {

    @GET(value = "/calendar/?format=json")
    suspend fun getAllCalendarEvents(): Response<CalendarEvents> // coroutines are used

}