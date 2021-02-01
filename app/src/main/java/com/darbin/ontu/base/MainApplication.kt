package com.darbin.ontu.base

import android.app.Application
import com.darbin.ontu.di.components.AppComponents
import com.darbin.ontu.di.components.DaggerAppComponents
import com.darbin.ontu.di.modules.AppModule
import com.darbin.ontu.di.modules.SharedPrefModule

open class MainApplication : Application() {

    companion object {
        lateinit var appComponents: AppComponents
    }

    override fun onCreate() {
        super.onCreate()
        appComponents = initDagger(this)
    }

    private fun initDagger(mainApplication: MainApplication): AppComponents =
        DaggerAppComponents.builder()
            .appModule(AppModule(mainApplication))
            .sharedPrefModule(SharedPrefModule((mainApplication)))
            .build()
}