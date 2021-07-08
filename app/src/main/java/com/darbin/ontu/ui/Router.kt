package com.darbin.ontu.ui

import android.app.Activity
import android.content.Intent
import com.darbin.ontu.ui.fileslisting.ActivityFilesListing
import com.darbin.ontu.utils.CURRENT_TAB
import javax.inject.Singleton

/**
 * Created by Aakib
 * Date : 08/July/2021
 * Project : Ontu
 */
@Singleton
class Router {

    fun showFilesListing(sourceActivity: Activity, currentTab: String) {
        val intent = Intent(sourceActivity, ActivityFilesListing::class.java)
        intent.putExtra(CURRENT_TAB, currentTab)
        sourceActivity.startActivity(intent)
    }

}