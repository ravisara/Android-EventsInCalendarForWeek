package com.example.eventsincalendarforweek.utility

import org.junit.Test

import org.junit.Assert.*

class HelperFunctionsKtTest {

    @Test
    fun getStartAndEndDateTimeStampsOfCurrentWeekTest() {
        val (startDate, endDate) = getStartAndEndDateTimeStampsOfCurrentWeek()
        assertEquals("2021-05-03T00:00:00Z", startDate)
        assertEquals("2021-05-09T23:59:59Z", endDate)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_the_converted_UTC_time_falls_on_same_day() {
        val theResult = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString("2021-05-09T10:00:00+04:00")
        assertEquals("Sun 09 05 2021 06:00 UTC", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_the_UTC_time_falls_on_following_day() {
        val theResult = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString("2021-05-09T23:00:00-04:00")
        assertEquals("Mon 10 05 2021 03:00 UTC", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_the_UTC_time_falls_on_previous_day() {
        val theResult = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString("2021-05-09T00:30:00+04:00")
        assertEquals("Sat 08 05 2021 20:30 UTC", theResult)
    }

    // TODO modify these tests to get the required result
    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_the_when_no_UTC_offset_is_there_result_is_OK() {
        val theResult = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString("2021-05-09T10:00:00Z")
        assertEquals("Sun 09 05 2021 10:00 UTC", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToHumanReadableUTCDateTimeString_when_UTC_converted_time_is_on_same_day() {
        val theResult = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString("2021-05-09T10:00:00+04:00")
        assertEquals("Sun 09 05 2021 06:00 UTC", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_result_time_falls_in_same_day_it_works() {
        val theResult = convertISO8601DateTimeStringToUTCDateTimeString("2021-05-09T10:00:00+04:00")
        assertEquals("202105090600", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_result_time_falls_in_following_day_it_works() {
        val theResult = convertISO8601DateTimeStringToUTCDateTimeString("2021-05-09T23:00:00-04:00")
        assertEquals("202105100300", theResult)
    }

    @Test
    fun convertISO8601DateTimeStringToUTCDateTimeString_when_result_time_has_two_non_zero_digits_it_works() {
        val theResult = convertISO8601DateTimeStringToUTCDateTimeString("2021-05-09T23:00:00+02:00")
        assertEquals("202105092100", theResult)
    }

}