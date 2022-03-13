package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sijobs.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {

    lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}