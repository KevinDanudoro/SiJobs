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
            Intent(this, LoginActivity::class.java).also {
                it.putExtra("HAS_SHOW", true)
                startActivity(it)
            }
            finish()
        }
    }
}