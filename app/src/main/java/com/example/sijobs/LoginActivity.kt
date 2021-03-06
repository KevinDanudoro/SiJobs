package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.window.SplashScreen
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

        val hasShow = intent.getBooleanExtra("HAS_SHOW", false)
        checkUser(hasShow)

        // Authentikasi email dan password
        binding.button.setOnClickListener {
            validasiData()
        }

        binding.regBtn.setOnClickListener {
            moveToRegister()
        }

    }

    private fun checkUser(hasShow: Boolean) {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else if(!hasShow){
            startActivity(Intent(this, GetStartedActivity::class.java))
            finish()
        }
    }

    private fun validasiData() {
        // trim() memotong spasi di awal dan akhir kalimat pada string
        email = binding.emailInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()
        var isError = false

        if(TextUtils.isEmpty(email)){
            binding.emailInput.error = "Please enter your email"
            isError = true
        }
        if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Please enter your password"
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
                Toast.makeText(this, "Login success with email $email", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // Login Gagal
                Toast.makeText(this, "Email or password wrong", Toast.LENGTH_LONG).show()
            }
    }


    private fun moveToRegister(){
        Intent(this, RegisterActivity::class.java).also{
            startActivity(it)
        }
    }

}

