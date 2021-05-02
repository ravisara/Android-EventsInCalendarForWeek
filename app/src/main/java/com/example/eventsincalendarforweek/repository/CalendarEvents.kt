package com.example.eventsincalendarforweek.repository

data class CalendarEvents(
    val count: Int,
    val next: String?, // when in the last page, next becomes null
    val previous: String?, // when in the first page, previous becomes null
    val results: List<Result>
)