package com.example.webkeyz_task.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    lateinit var binding :ActivitySplashBinding
    lateinit var job :Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animSlide = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.splash_anim
        )
        binding.ivLogo.startAnimation(animSlide)

        job =CoroutineScope(Dispatchers.Main).launch{
            delay(1000)
            navigate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun navigate(){

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}