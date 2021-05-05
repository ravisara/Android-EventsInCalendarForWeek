package com.example.eventsincalendarforweek.viewmodel

import Resource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.eventsincalendarforweek.repository.EventsDataRepository
import com.example.eventsincalendarforweek.utility.convertISO8601DateTimeStringToHumanReadableUTCDateTimeString
import com.example.eventsincalendarforweek.utility.convertISO8601DateTimeStringToUTCDateTimeString
import com.example.eventsincalendarforweek.utility.getStartAndEndDateTimeStampsOfCurrentWeek
import com.example.eventsincalendarforweek.repository.Result
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // TODO review whether application is needed.
    var eventsDataRepository = EventsDataRepository.getInstance(application)

    /*private val _calendarEventsResponse =  MutableLiveData<Response<CalendarEvents>>()
    val calendarEventsResponse: LiveData<Response<CalendarEvents>> = _calendarEventsResponse // as per the best practice not to expose mutable live data to the UI*/

    /*
    private val _calendarEventsResponse = MutableLiveData<Resource<Response<CalendarEvents>>>()
    val calendarEventsResponse: LiveData<Resource<Response<CalendarEvents>>> =
        _calendarEventsResponse // as per the best practice not to expose mutable live data to the UI
    */

    // Was planning to use the same Resource<Response<CalendarEvents>> returned from the repository. But realized that was not going to work and had to quickly present this solution.
    private val _tempSolution = MutableLiveData<Resource<List<Result>>>()
    val tempSolution: LiveData<Resource<List<Result>>> = _tempSolution // as per the best practice not to expose mutable live data to the UI

    fun getCalendarEventsInCurrentWeek() {

        val (startDateTime, endDateTime) = getStartAndEndDateTimeStampsOfCurrentWeek()

        viewModelScope.launch {

            // first get initial events returned which are between the specified times. There could be more than one page and this will only return the first page if there are multiple pages.
            val firstsResponseResource = eventsDataRepository.getCalendarEventsInSpecifiedInterval(
                startDateTime,
                endDateTime
            )

            // If the request was successful, check if there is another page until the next page field is null
            if (firstsResponseResource.status == Status.SUCCESS) {

                // add the results obtained from the first query to a list to be used for displaying data later
                var apiQueryResponseBodyResults =
                    firstsResponseResource.data!!.body()!!.results.toMutableList() // when Status is "SUCCESS" the ones with "!!" will not be null (has been checked earlier)

                val nextURL = firstsResponseResource.data.body()!!.next
                if (nextURL != null) { // this means there is another page! Another API call has to be made to fetch the next page.
                    // TODO this has to be done in a loop until the nextURL is null as we can't just stop from the second date. Ran out off time to do this
                    val nextResponseResource =
                        eventsDataRepository.getCalendarEventsByURL(nextURL)
                    if (nextResponseResource.status == Status.SUCCESS) {
                        apiQueryResponseBodyResults.addAll(nextResponseResource.data!!.body()!!.results)
                    }
                }

                // for each result, replace the start date time and end date time with human readable counterparts. Also, populate the property that is going to be used for sorting by start date time.
                for ((idx, result) in apiQueryResponseBodyResults.withIndex()) {
                    apiQueryResponseBodyResults[idx].startDateTimeWithOnlyDigitsForSorting = convertISO8601DateTimeStringToUTCDateTimeString(result.start) // this property is set to null by retrofit as there is no property called startDateTimeWithOnlyDigitsForSorting in the json response. TODO to make the code cleaner it is best not to modify the Result class and handle this in a different way.
                    apiQueryResponseBodyResults[idx].start = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString(result.start)
                    apiQueryResponseBodyResults[idx].end = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString(result.end)
                }

                val finalResultsToShow = apiQueryResponseBodyResults.sortedBy { it.startDateTimeWithOnlyDigitsForSorting }

                _tempSolution.value = Resource.success(finalResultsToShow)

            } else {
                _tempSolution.value = Resource.error("Some error", null)
            }

        }

    }


}