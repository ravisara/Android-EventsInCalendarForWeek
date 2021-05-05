package com.example.eventsincalendarforweek.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuzzHireAPI {

    @GET(value = "/calendar/?format=json")
    suspend fun getAllCalendarEvents(): Response<CalendarEvents> // suspend as coroutines are used

    @GET(value = "/calendar/?format=json")
    suspend fun getCalendarEventsInSpecifiedInterval(@Query("since") startDateTime: String, @Query("before") endDateTime: String): Response<CalendarEvents>

}