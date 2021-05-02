package com.example.eventsincalendarforweek.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.eventsincalendarforweek.R
import com.example.eventsincalendarforweek.repository.RetrofitInstance
import java.io.IOException
import java.lang.Exception

const val TAG = "MainFragmentError"
// TODO use view data binding
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

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
                RetrofitInstance.api.getAllCalendarEvents()
            } catch (e: Exception) {
                Log.e(TAG, "onActivityCreated: error fetching calendar events from the API. More information: ${e.message}", )
            }
            Log.i(TAG, response.toString())
        }

    }

    private fun setupRecyclerView() {

    }


}