package com.example.eventsincalendarforweek.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventsincalendarforweek.R
import com.example.eventsincalendarforweek.repository.RetrofitInstance
import com.example.eventsincalendarforweek.utility.convertISO8601DateTimeStringToHumanReadableUTCDateTimeString
import com.example.eventsincalendarforweek.utility.convertISO8601DateTimeStringToUTCDateTimeString
import com.example.eventsincalendarforweek.utility.getStartAndEndDateTimeStampsOfCurrentWeek
import com.example.eventsincalendarforweek.viewmodel.MainViewModel
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainFragmentError"
// TODO use view data binding
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var theRecyclerView : RecyclerView
    private lateinit var customAdapterForRecyclerView: CustomAdapterForRecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        lifecycleScope.launchWhenCreated {
            val response = try {
                //RetrofitInstance.api.getAllCalendarEvents() // for testing
                val (startDateTime, endDateTime) = getStartAndEndDateTimeStampsOfCurrentWeek()
                //RetrofitInstance.api.getCalendarEventsInSpecifiedInterval(startDateTime = "2020-06-09T13:00:00+01:00", endDateTime = "2020-06-09T17:10:00+01:00") // for testing
                RetrofitInstance.api.getCalendarEventsInSpecifiedInterval(startDateTime, endDateTime)
            } catch(e: IOException) {
                Log.e(TAG, "IOException, check your internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            } catch (e: Exception) { // For unforseen circumstances
                Log.e(TAG, "onActivityCreated: error fetching calendar events from the API. More information: ${e.message}")
                return@launchWhenCreated
            }
            Log.i(TAG, response.toString())
            if(response.isSuccessful && response.body() != null) {
                val apiQueryResponseBodyResults = response.body()!!.results.toMutableList() // is of type List<Result>

                // for each result, replace the start date time and end date time with human readable counterparts. Also, populate the property that is going to be used for sorting by start date time.
                for ((idx, result) in apiQueryResponseBodyResults.withIndex()) {
                    apiQueryResponseBodyResults[idx].startDateTimeWithOnlyDigitsForSorting = convertISO8601DateTimeStringToUTCDateTimeString(result.start) // this property is set to null by retrofit as there is no property called startDateTimeWithOnlyDigitsForSorting in the json response. TODO to make the code cleaner it is best not to modify the Result class and handle this in a different way.
                    apiQueryResponseBodyResults[idx].start = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString(result.start)
                    apiQueryResponseBodyResults[idx].end = convertISO8601DateTimeStringToHumanReadableUTCDateTimeString(result.end)
                }

                // TODO handle multiple pages. I.e. although for one week with the current data set for the week begninng on 3rd May 2021 only one page is returned, it is quite possible that more thank one page is returned for another week.
                if (response.body()!!.next != null) { // this means there is another page! Another API call has to be made

                }

                val finalResultsToShow = apiQueryResponseBodyResults.sortedBy{ it.startDateTimeWithOnlyDigitsForSorting }

                customAdapterForRecyclerView = CustomAdapterForRecyclerView(finalResultsToShow)
                setupRecyclerView()
            }

        }

    }

    /**
     * Called immediately after [.onCreateView]
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        theRecyclerView = view.findViewById(R.id.eventsInWeekRecyclerView)
    }

    private fun setupRecyclerView() = theRecyclerView.apply {
        adapter = customAdapterForRecyclerView
        layoutManager = LinearLayoutManager(requireContext())
    }


}