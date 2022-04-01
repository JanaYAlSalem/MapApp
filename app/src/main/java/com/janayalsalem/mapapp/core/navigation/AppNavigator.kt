package com.janayalsalem.mapapp.core.navigation

interface AppNavigator {
    fun navigateTo(screen:Screen)
}

enum class Screen{
    MAP,
    PLACES
}