package com.darbin.ontu.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darbin.ontu.ui.dashboard.HomeViewModel
import com.darbin.ontu.ui.fileslisting.viewmodels.ImagesViewModel
import com.darbin.ontu.ui.onboarding.OnBoardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingVM(onBoardingViewModel: OnBoardingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeVM(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    abstract fun bindImageVM(imagesViewModel: ImagesViewModel): ViewModel

}