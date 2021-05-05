package com.example.eventsincalendarforweek.utility

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

// different variations of formats in returned by the API:
// 2020-06-09T13:30:00+01:00
// 2020-03-02T08:30:00Z
// The following times all refer to the same moment: "18:30Z", "22:30+04", "1130−0700", and "15:00−03:30".

/*
 This class is used to find the start date and end date of the current week. It is used by a method below.
 Adapted from the following answer: https://stackoverflow.com/a/22890763/12247532
 TODO adjust the order and simplify
*/
class ThisLocalizedWeek(specifiedLocale: Locale) {

    private val locale: Locale = specifiedLocale

    private val firstDayOfWeek: DayOfWeek = WeekFields.of(specifiedLocale).firstDayOfWeek

    private val lastDayOfWeek: DayOfWeek = DayOfWeek.of((firstDayOfWeek.value + 5) % DayOfWeek.values().size + 1)

    val firstDay: LocalDate
        get() = LocalDate.now(TZ).with(TemporalAdjusters.previousOrSame(firstDayOfWeek))

    val lastDay: LocalDate
        get() = LocalDate.now(TZ).with(TemporalAdjusters.nextOrSame(lastDayOfWeek))

    companion object {  // TODO check if companion can be removed.
        // time zone being worked with
        private val TZ: ZoneId = ZoneId.of("UTC")
    }

    override fun toString(): String {
        return java.lang.String.format("The %s week starts on %s and ends on %s",
                locale.displayName,
                firstDayOfWeek,
                lastDayOfWeek)
    }

}

data class StartAndEndDateTimeStampsOfCurrentWeek(val start: String, val end: String)
fun getStartAndEndDateTimeStampsOfCurrentWeek(): StartAndEndDateTimeStampsOfCurrentWeek {

    // Keeping the locale on the UK one for the time being for simplicity. In a production app the locale of the users phone has to be used. Start of the week is Monday and the end of the week is Sunday in this scheme.
    val ukWeek = ThisLocalizedWeek(Locale.UK);

    val firstDateTimeOfWeek = ukWeek.firstDay.toString() + "T00:00:00Z"
    val lastDateTimeOfWeek = ukWeek.lastDay.toString() + "T23:59:59Z"

    return StartAndEndDateTimeStampsOfCurrentWeek(firstDateTimeOfWeek, lastDateTimeOfWeek)

}

fun convertISO8601DateTimeStringToHumanReadableUTCDateTimeString(dateTimeStringInISO8601: String): String {

    val offsetDateTime = OffsetDateTime.parse(dateTimeStringInISO8601)
    val dateTimeInUTC = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC)

    val formatter = DateTimeFormatter.ofPattern("E dd MM yyyy HH:mm") // E for day of the week(Mon, Tue etc.)
    return dateTimeInUTC.format(formatter)

}

// The value returned by this method will be used for sorting results in ascending order of the starting date
fun convertISO8601DateTimeStringToUTCDateTimeString(dateTimeStringInISO8601: String): String {

    val offsetDateTime = OffsetDateTime.parse(dateTimeStringInISO8601)
    val dateTimeInUTC = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC)

    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    return dateTimeInUTC.format(formatter)

}