package com.darbin.ontu.ui.dashboard

import androidx.lifecycle.ViewModel
import com.darbin.ontu.R
import com.darbin.ontu.data.models.Friend
import javax.inject.Inject

class HomeViewModel @Inject constructor(
) : ViewModel() {

    fun getFriendsList(): ArrayList<Friend> {
        val classList = ArrayList<Friend>()
        classList.add(Friend())
        classList.add(Friend(R.drawable.img_1))
        classList.add(Friend(R.drawable.img_2))
        classList.add(Friend(R.drawable.img_3))
        classList.add(Friend(R.drawable.img_4))
        classList.add(Friend(R.drawable.img_5))
        classList.add(Friend(R.drawable.img_6))
        classList.add(Friend(R.drawable.img_7))
        classList.add(Friend(R.drawable.img_8))
        return classList
    }
}