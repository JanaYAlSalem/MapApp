package com.janayalsalem.mapapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.janayalsalem.mapapp.R
import com.janayalsalem.mapapp.ui.feature.map.PlacesMapsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.homeContainer , PlacesMapsFragment()).commit()
        } // end if
    }
}