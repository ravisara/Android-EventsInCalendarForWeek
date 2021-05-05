package com.example.eventsincalendarforweek.ui.main

import Resource
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventsincalendarforweek.R
import com.example.eventsincalendarforweek.viewmodel.MainViewModel
import com.example.eventsincalendarforweek.repository.Result

const val TAG = "MainFragmentError"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var theRecyclerView: RecyclerView
    private lateinit var customAdapterForRecyclerView: CustomAdapterForRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
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

        val viewModel: MainViewModel by viewModels()

        viewModel.getCalendarEventsInCurrentWeek()

        val responseObserver: Observer<Resource<List<Result>>> = Observer { resource ->
            Log.i(TAG, resource.toString())
            if (resource.status == Status.SUCCESS) {
                customAdapterForRecyclerView = CustomAdapterForRecyclerView(resource.data!!)
                setupRecyclerView()
            } else if (resource.status == Status.LOADING) {
                // TODO show a progress bar (ran out of time to do this)
            } else if (resource.status == Status.ERROR) {
                // TODO show an error message (ran out of time to do this)
            }
        }

        viewModel.tempSolution.observe(viewLifecycleOwner, responseObserver)
    }

    private fun setupRecyclerView() = theRecyclerView.apply {
        adapter = customAdapterForRecyclerView
        layoutManager = LinearLayoutManager(requireContext())
    }

}

