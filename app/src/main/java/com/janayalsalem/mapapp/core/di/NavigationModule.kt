package com.janayalsalem.mapapp.core.di

import com.janayalsalem.mapapp.core.navigation.AppNavigator
import com.janayalsalem.mapapp.core.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {
    @Binds
    abstract fun bindsAppNavigator(appNavigator: AppNavigatorImpl) : AppNavigator
}