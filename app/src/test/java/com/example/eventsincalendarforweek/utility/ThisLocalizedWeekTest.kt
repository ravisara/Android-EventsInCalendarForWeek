package com.example.eventsincalendarforweek.utility

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ThisLocalizedWeekTest {

    @Test
    fun getFirstDay() {
    }

    @Test
    fun getLastDay() {
    }

    @Test
    fun testToString() {
    }

    @Test
    fun testTheFunction() {// TODO rename

        val usWeek = ThisLocalizedWeek(Locale.UK);
        System.out.println(usWeek);
        // The English (United States) week starts on SUNDAY and ends on SATURDAY
        System.out.println(usWeek.firstDay); // 2018-01-14
        System.out.println(usWeek.lastDay);  // 2018-01-20

    }
}