package com.example.sijobs

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.sijobs.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class RegisterActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityRegisterBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var username: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Interaksi tombol register
        binding.button.setOnClickListener {
            validasiData()
        }

        // Mengambil data tanggal kelahiran
        binding.birthInput.setOnClickListener{
            datePicker()
        }
    }


    private fun validasiData() {
        username = binding.usernameInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()

        if(TextUtils.isEmpty(username)){
            binding.usernameInput.error = "Please Enter The Username"
        }
        else if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Please Enter The Password"
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            binding.usernameInput.error = "Format Penulisan Email Tidak Sesuai"
        }
        else if(password.length < 6){
            binding.passInput.error = "Password Terlalu Lemah (min 7 karakter)"
        }
        else{
            firebaseRegister()
        }
    }


    private fun firebaseRegister() {
        firebaseAuth.createUserWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                val email = firebaseAuth.currentUser!!.email
                Toast.makeText(this, "Berhasil registrasi menggunakan username $email", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Gagal melakukan registrasi sebab ${it.message}", Toast.LENGTH_LONG).show()
            }
    }


    // Kalender untuk mengisi data
    private fun datePicker(){
        //Calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { datePicker, mYear, mMonth, mDay ->
            //set to edittext
            binding.birthInput.setText("$mDay/$mMonth/$mYear")
        },year,month, day)
        dpd.show()
    }
}