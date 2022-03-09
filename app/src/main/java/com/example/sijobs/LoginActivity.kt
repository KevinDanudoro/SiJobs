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
    private lateinit var username: String
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
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    private fun validasiData() {
        // trim() memotong spasi di awal dan akhir kalimat pada string
        username = binding.usernameInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()

        if(TextUtils.isEmpty(username)){
            binding.usernameInput.error = "Please Enter The Username"
        }
        else if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Please Enter The Password"
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                // Login Berhasil
                Toast.makeText(this, "Login berhasil sebagai $username", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // Login Gagal
                Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_LONG).show()
            }
    }


    private fun moveToRegister(){
        Intent(this, RegisterActivity::class.java).also{
            startActivity(it)
        }
    }

}