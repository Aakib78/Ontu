package com.darbin.ontu.di.modules

import android.app.Application
import android.content.Context
import com.darbin.ontu.data.prefs.AppPreferenceImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule(private val application: Application) {

    private var mPrefs = application.getSharedPreferences("Ontu_Share_Prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPref(): AppPreferenceImp {
        return AppPreferenceImp(mPrefs)
    }
}