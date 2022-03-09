package com.example.sijobs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sijobs.databinding.ActivityProfileBinding

private lateinit var binding: ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}