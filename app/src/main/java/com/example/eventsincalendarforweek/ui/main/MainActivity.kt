package com.example.eventsincalendarforweek.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventsincalendarforweek.R
import com.example.eventsincalendarforweek.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}