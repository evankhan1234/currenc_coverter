package com.currency.exchanger.presentation.spalash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.currency.exchanger.R
import com.currency.exchanger.databinding.ActivitySplashBinding
import com.currency.exchanger.infra.utils.Network
import com.currency.exchanger.infra.utils.SomeUtils
import com.currency.exchanger.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            if(Network.checkConnectivity(this@SplashActivity)){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            else{
                SomeUtils.showPopUp(this@SplashActivity,getString(R.string.network))
            }

        }, 2000)
    }

}