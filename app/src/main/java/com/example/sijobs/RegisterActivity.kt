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
    private lateinit var username: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Interaksi tombol register
        binding.button.setOnClickListener {
            validasiData()
        }

        // Mengambil data tanggal kelahiran
        binding.birthInput.setOnClickListener{
            datePicker()
        }
    }

    // Validasi data email dan password sebelum di register
    private fun validasiData() {
        username = binding.emailInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()
        var isError = false

        if(TextUtils.isEmpty(username)){
            binding.emailInput.error = "Mohon masukkan email"
            isError = true
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            binding.emailInput.error = "Format Penulisan Email Tidak Sesuai"
            isError = true
        }

        if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Mohon masukkan password"
            isError = true
        }
        else if(password.length < 6){
            binding.passInput.error = "Password Terlalu Lemah (min 7 karakter)"
            isError = true
        }
        if(!isError){
            firebaseRegister()
        }
    }

    // Membaut akun di firebase berdasarkan input email dan password yang dimasukkan
    private fun firebaseRegister() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                // Jika register berhasil
                val email = FirebaseAuth.getInstance().currentUser!!.email
                Toast.makeText(this, "Berhasil registrasi menggunakan username $email", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // Jika register gagal
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