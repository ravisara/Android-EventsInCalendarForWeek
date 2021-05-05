package com.example.eventsincalendarforweek.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO combine this with the API interface in the same file
object RetrofitInstance {

    val api: BuzzHireAPI by lazy { // so that this is created at first use and the result is stored.
        Retrofit.Builder()
                .baseUrl("https://assessments.bzzhr.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BuzzHireAPI::class.java)
    }

}
