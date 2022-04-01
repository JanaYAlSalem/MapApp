package com.janayalsalem.mapapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.janayalsalem.mapapp.R
import com.janayalsalem.mapapp.core.navigation.AppNavigator
import com.janayalsalem.mapapp.core.navigation.Screen
import com.janayalsalem.mapapp.ui.feature.map.PlacesMapsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            appNavigator.navigateTo(Screen.MAP)
//            supportFragmentManager.beginTransaction().replace(R.id.homeContainer , PlacesMapsFragment()).commit()
        } // end if


    }

    override fun onBackPressed() {
        super.onBackPressed()
        //handle backstack
        if(supportFragmentManager.backStackEntryCount == 0)
            finish()
    }
}