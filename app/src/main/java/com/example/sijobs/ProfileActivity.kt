package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sijobs.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pindah ke halaman Change Profile
        binding.changeProfile.setOnClickListener { toChangeProfile() }


        // Cek user masih login apa sudah logout
        checkUser()
        // Jika user logout maka
        binding.logout.setOnClickListener { userLogout() }
    }

    private fun toChangeProfile() {
        startActivity(Intent(this, NewProfileActivity::class.java))
    }

    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun checkUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}