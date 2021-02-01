package com.darbin.ontu.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darbin.ontu.R
import com.darbin.ontu.base.MainApplication
import com.darbin.ontu.data.prefs.AppPreferenceImp
import com.darbin.ontu.ui.dashboard.HomeActivity
import com.darbin.ontu.ui.onboarding.ActivityOnBoarding
import kotlinx.coroutines.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var appPreferenceImp: AppPreferenceImp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        appComponents.inject(this)
        showOnBoardingActivity()
    }

    private fun showOnBoardingActivity(){
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000L)
            if (appPreferenceImp.getUser()!=null){
                startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashActivity,ActivityOnBoarding::class.java))
                finish()
            }
        }
    }
}