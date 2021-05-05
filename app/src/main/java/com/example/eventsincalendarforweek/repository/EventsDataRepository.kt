package com.example.eventsincalendarforweek.repository

import Resource
import android.app.Application
import android.util.Log
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.io.IOException

const val TAG = "EventsDataRepository"
class EventsDataRepository private constructor(application: Application){

    companion object : SingletonHolder<EventsDataRepository, Application>(::EventsDataRepository)

    // TODO propagate any errors to the UI layer Response<CalendarEvents> in a better way than the current hacked-together solution in a hurry.
    suspend fun getCalendarEventsInSpecifiedInterval(startDateTime: String, endDateTime: String): Resource<Response<CalendarEvents>> {

        val response = try {
            Resource.loading(null)
            RetrofitInstance.api.getCalendarEventsInSpecifiedInterval(startDateTime, endDateTime)
            //RetrofitInstance.api.getAllCalendarEvents() // for testing a response with multiple pages, this line has been temporarily added
        } catch(e: IOException) {
            Log.e(TAG, "IOException, check your internet connection")
            return Resource.error("IOException, check your internet connection. More info: ${e.message}", null)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return Resource.error("HttpException, unexpected response. More info ${e.message}", null)
        } catch (e: Exception) { // For unforseen circumstances
            Log.e(TAG, "Unknown error fetching calendar events from the API. More information: ${e.message}")
            return Resource.error("Unknown error fetching calendar events from the API. More information: ${e.message}", null)
        }

        Log.i(TAG, response.toString())

        return if (response.isSuccessful && response.body() != null) {
            Resource.success(response)
        } else {
            Resource.error("Response received from the server but the body is likely to be empty", null)
        }

    }

    suspend fun getCalendarEventsByURL(fullURL: String): Resource<Response<CalendarEvents>> {

        val response = try {
            Resource.loading(null)
            RetrofitInstance.api.getCalendarEventsByURL(fullURL) // for testing a response with multiple pages, this line has been temporarily added
        } catch(e: IOException) {
            Log.e(TAG, "IOException, check your internet connection")
            return Resource.error("IOException, check your internet connection. More info: ${e.message}", null)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return Resource.error("HttpException, unexpected response. More info ${e.message}", null)
        } catch (e: Exception) { // For unforseen circumstances
            Log.e(TAG, "Unknown error fetching calendar events from the API. More information: ${e.message}")
            return Resource.error("Unknown error fetching calendar events from the API. More information: ${e.message}", null)
        }

        Log.i(TAG, response.toString())

        return if (response.isSuccessful && response.body() != null) {
            Resource.success(response)
        } else {
            Resource.error("Response received from the server but the body is likely to be empty", null)
        }

    }

}

