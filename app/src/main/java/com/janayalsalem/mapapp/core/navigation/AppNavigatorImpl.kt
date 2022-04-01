package com.janayalsalem.mapapp.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.janayalsalem.mapapp.R
import com.janayalsalem.mapapp.core.navigation.Screen.*
import com.janayalsalem.mapapp.ui.feature.map.PlacesMapsFragment
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private  val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screen) {
        val fragment = when(screen){
            MAP -> PlacesMapsFragment()
//            Screen.PLACES -> PDetails.newInstance(restaurant)
            PLACES -> TODO()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, fragment as Fragment)
            .addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }
}