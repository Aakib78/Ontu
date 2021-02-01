package com.darbin.ontu.utils

import android.os.Build

class DeviceUtils {

    companion object{
        fun getDeviseName():String{
            return Build.MANUFACTURER + " " + Build.MODEL
        }
    }
}