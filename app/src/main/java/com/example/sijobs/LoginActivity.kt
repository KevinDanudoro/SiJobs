package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.sijobs.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Binding ke XML
    private lateinit var binding: ActivityLoginBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inisialisasi auth firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // Authentikasi email dan password
        binding.button.setOnClickListener {

            validasiData()
        }

        binding.regBtn.setOnClickListener {
            moveToRegister()
        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validasiData() {
        // trim() memotong spasi di awal dan akhir kalimat pada string
        email = binding.emailInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()
        var isError = false

        if(TextUtils.isEmpty(email)){
            binding.emailInput.error = "Mohon masukkan email"
            isError = true
        }
        if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Mohon masukkan password"
            isError = true
        }
        if(!isError){
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Login Berhasil
                Toast.makeText(this, "Login berhasil sebagai $email", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // Login Gagal
                Toast.makeText(this, "Email atau Password salah", Toast.LENGTH_LONG).show()
            }
    }


    private fun moveToRegister(){
        Intent(this, RegisterActivity::class.java).also{
            startActivity(it)
        }
    }

}