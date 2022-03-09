package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sijobs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Fitur Pindah halaman ke ActivityNewProfile
        binding.button.setOnClickListener {
            moveToNewProfile()
        }

        binding.regBtn.setOnClickListener {
            moveToRegister()
        }

    }


    private fun moveToRegister(){
        Intent(this, RegisterActivity::class.java).also{
            startActivity(it)
        }
    }

    private fun moveToNewProfile(){
        // ubah editable text ke string
        val username = binding.usernameInput.text.toString()
        val password = binding.passInput.text.toString()

        // Buat intent untuk mengarahkan activity ini ke newProfileActivity
        Intent(this, ProfileActivity::class.java).also{
            // Pasangan antara key dan value untuk mengirimkan data pada editable text ke activity lain
            it.putExtra("USERNAME", username)
            it.putExtra("PASSWORD", password)

            startActivity(it) // Pindah ke halaman yg diatur Intent
        }
    }
}