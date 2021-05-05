package com.example.eventsincalendarforweek.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.eventsincalendarforweek.repository.CalendarEvents
import com.example.eventsincalendarforweek.repository.EventsDataRepository
import com.example.eventsincalendarforweek.utility.getStartAndEndDateTimeStampsOfCurrentWeek
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {

    // TODO review whether application is needed.
    var eventsDataRepository = EventsDataRepository.getInstance(application)

    private val _calendarEventsResponse =  MutableLiveData<Response<CalendarEvents>>()
    val calendarEventsResponse: LiveData<Response<CalendarEvents>> = _calendarEventsResponse // as per the best practice not to expose mutable live data to the UI

    fun getCalendarEventsInCurrentWeek() {

        val (startDateTime, endDateTime) = getStartAndEndDateTimeStampsOfCurrentWeek()

        viewModelScope.launch {
            _calendarEventsResponse.value = eventsDataRepository.getCalendarEventsInSpecifiedInterval(startDateTime, endDateTime)
        }

    }

}