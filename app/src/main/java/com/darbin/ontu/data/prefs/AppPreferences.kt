package com.darbin.ontu.data.prefs

import com.darbin.ontu.data.models.UserModel

interface AppPreferences {

    fun getUser(): UserModel?

    fun setUser(user: UserModel?)
}