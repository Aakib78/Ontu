package com.darbin.ontu.data.prefs

import android.content.SharedPreferences
import com.darbin.ontu.data.models.UserModel
import com.darbin.ontu.utils.PREF_KEY_USER
import com.google.gson.Gson

class AppPreferenceImp(private val mPrefs: SharedPreferences): AppPreferences {

    override fun getUser(): UserModel? {
        val userPref = mPrefs.getString(PREF_KEY_USER, null)
        return Gson().fromJson(userPref, UserModel::class.java)
    }

    override fun setUser(user: UserModel?) {
        val userPref = Gson().toJson(user)
        mPrefs.edit().putString(PREF_KEY_USER, userPref).apply()
    }
}