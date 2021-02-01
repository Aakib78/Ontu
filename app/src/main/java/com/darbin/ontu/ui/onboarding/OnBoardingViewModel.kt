package com.darbin.ontu.ui.onboarding

import androidx.lifecycle.ViewModel
import com.darbin.ontu.R
import com.darbin.ontu.data.models.AvatarModel
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
) : ViewModel() {


    fun getAvatarList(): ArrayList<AvatarModel> {
        val avatarList = ArrayList<AvatarModel>()
        avatarList.add(AvatarModel(R.drawable.avatar1))
        avatarList.add(AvatarModel(R.drawable.avatar2))
        avatarList.add(AvatarModel(R.drawable.avatar3))
        avatarList.add(AvatarModel(R.drawable.avatar4))
        avatarList.add(AvatarModel(R.drawable.avatar5))
        avatarList.add(AvatarModel(R.drawable.avatar6))
        avatarList.add(AvatarModel(R.drawable.avatar7))
        avatarList.add(AvatarModel(R.drawable.avatar8))
        return avatarList
    }

}