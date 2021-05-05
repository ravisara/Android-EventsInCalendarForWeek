/*
package com.example.eventsincalendarforweek.repository

import retrofit2.Call
import retrofit2.Retrofit

class EventsDataRepository {

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build()

        val service: GitHubService = retrofit.create(GitHubService::class.java)
    }

    fun fetchRepos() {
        val repos: Call<List<Repo>> = service.listRepos("octocat")
    }


}*/
