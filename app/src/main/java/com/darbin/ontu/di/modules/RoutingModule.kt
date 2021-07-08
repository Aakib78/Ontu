package com.darbin.ontu.di.modules

import com.darbin.ontu.ui.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Aakib
 * Date : 08/July/2021
 * Project : Ontu
 */

@Module
class RoutingModule () {

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return Router()
    }
}