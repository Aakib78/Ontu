package com.darbin.ontu.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.darbin.ontu.R
import com.darbin.ontu.base.MainApplication
import com.darbin.ontu.custom.StartSnapHelper
import com.darbin.ontu.data.models.AvatarModel
import com.darbin.ontu.data.models.UserModel
import com.darbin.ontu.data.prefs.AppPreferenceImp
import com.darbin.ontu.databinding.ActivityOnBoardingBinding
import com.darbin.ontu.ui.dashboard.HomeActivity
import com.darbin.ontu.utils.DeviceUtils
import com.darbin.ontu.utils.viewModelProvider
import javax.inject.Inject

class ActivityOnBoarding : AppCompatActivity() {

    private val appComponents by lazy { MainApplication.appComponents }

    private var userAvatar=R.drawable.avatar0

    @Inject
    lateinit var appPreferenceImp: AppPreferenceImp

    private val avatarAdapter by lazy {
        AvatarAdapter { pos: Int, avatar: AvatarModel ->
            binding.rvAvatar.smoothScrollToPosition(pos)
            userAvatar=avatar.image
        } }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityOnBoardingBinding


    private fun getViewModel(): OnBoardingViewModel {
        return viewModelProvider(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        appComponents.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        initViews()
    }

    private fun initViews() {
        val snapHelper=StartSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvAvatar)
        binding.rvAvatar.initialize(avatarAdapter)
//        binding.rvAvatar.setViewsToChangeColor(listOf(R.id.img_avatar))
        avatarAdapter.setItems(getViewModel().getAvatarList())

        binding.btnStart.setOnClickListener {
            val userModel=UserModel(null,userAvatar,DeviceUtils.getDeviseName())
            if (binding.etUserName.text.isNullOrEmpty()){
                userModel.userName=DeviceUtils.getDeviseName()
            }else{
                userModel.userName=binding.etUserName.text.toString()
            }
            appPreferenceImp.setUser(userModel)
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
}