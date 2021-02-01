package com.darbin.ontu.di

import javax.inject.Qualifier

/**
 * Created by Aakib Saifi on 25,Dec,2020
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher