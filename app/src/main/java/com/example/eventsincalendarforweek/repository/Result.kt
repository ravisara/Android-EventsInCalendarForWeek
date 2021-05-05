package com.example.eventsincalendarforweek.repository

// All these variables could have been val s but had to make them var as start and startDateTimeWithOnlyDigitsForSorting fields are replaced later on with a different format of the respective values.
data class Result(
        var id: Int,
        var start: String,
        var startDateTimeWithOnlyDigitsForSorting: String?, // this is not coming from the API. this is used later on for sorting in ascending order of start date time. Not ideal, but quite handy.
        var end: String,
        var label: String,
        var category: String
)