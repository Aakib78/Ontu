package com.darbin.ontu.data.prefs

import android.content.SharedPreferences
import com.darbin.ontu.data.models.UserModel
import com.google.gson.Gson

class AppPreferenceImp(private val mPrefs: SharedPreferences): AppPreferences {
    private val PREF_KEY_USER = "PREF_KEY_USER"

    override fun getUser(): UserModel? {
        val userPref = mPrefs.getString(PREF_KEY_USER, null)
        return Gson().fromJson(userPref, UserModel::class.java)
    }

    override fun setUser(userModel: UserModel?) {
        val userPref = Gson().toJson(userModel)
        mPrefs.edit().putString(PREF_KEY_USER, userPref).apply()
    }
}