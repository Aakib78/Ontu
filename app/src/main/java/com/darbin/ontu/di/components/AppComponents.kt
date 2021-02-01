package com.darbin.ontu.di.components

import android.content.Context
import com.darbin.ontu.di.modules.AppModule
import com.darbin.ontu.di.modules.CoroutinesModule
import com.darbin.ontu.di.modules.SharedPrefModule
import com.darbin.ontu.ui.dashboard.HomeActivity
import com.darbin.ontu.ui.onboarding.ActivityOnBoarding
import com.darbin.ontu.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        CoroutinesModule::class,
        SharedPrefModule::class
    ]
)

interface AppComponents {

    fun context(): Context

    fun inject(mainActivity: HomeActivity)
    fun inject(activityOnBoarding: ActivityOnBoarding)
    fun inject(splashActivity: SplashActivity)
}