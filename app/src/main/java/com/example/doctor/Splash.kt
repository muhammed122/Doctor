package com.example.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Splash : AppCompatActivity() {

    val TIME_OUT = 3000L
    lateinit var topAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        topAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.top_animation)
//        splash_logo.animation = topAnimation

        CoroutineScope(Main).launch {
            navigateToMain()

        }

    }

    suspend fun navigateToMain() {
        delay(TIME_OUT)
        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }
}