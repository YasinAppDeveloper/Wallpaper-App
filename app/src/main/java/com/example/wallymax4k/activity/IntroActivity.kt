package com.example.wallymax4k.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.wallymax4k.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.progressBar.max = 100
        val handler = Handler()
        val runnable = object : Runnable{
            private var progress = 0
            override fun run() {
                if (progress < 100) {
                    progress += (100 / (3 * 10))
                    binding.progressBar.progress = progress
                    handler.postDelayed(this, 100)
                } else {
                    startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
      handler.post(runnable)


    }
}