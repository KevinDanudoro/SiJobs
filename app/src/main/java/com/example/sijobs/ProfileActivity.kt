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

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

//        checkUser()
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(this, "Selamat anda berhasil login dengan username ${firebaseUser.email}", Toast.LENGTH_LONG).show()
        }
    }
}