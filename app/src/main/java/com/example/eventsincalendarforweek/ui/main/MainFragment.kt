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
                RetrofitInstance.api.getAllCalendarEvents()
            } catch(e: IOException) {
                Log.e(TAG, "IOException, check your internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            /*} catch (e: Exception) {
                //Log.e(TAG, "onActivityCreated: error fetching calendar events from the API. More information: ${e.message}", )
            }*/
            Log.i(TAG, response.toString())
            if(response.isSuccessful && response.body() != null) {
                val resultsToShow = response.body()!!.results
                customAdapterForRecyclerView = CustomAdapterForRecyclerView(resultsToShow)
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