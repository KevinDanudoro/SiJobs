package com.example.sijobs

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.example.sijobs.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RegisterActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityRegisterBinding

    // Data user
    private lateinit var email: String
    private lateinit var password: String

    // Apa username perlu diinputkan pada app freelancer?
    // Mending first name aja ga sih?
    private lateinit var username: String
    private lateinit var dateOfBirth: String
    private lateinit var gender: String
    private lateinit var address: String


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
        email = binding.emailInput.text.toString().trim()
        password = binding.passInput.text.toString().trim()
        username = binding.usernameInput.text.toString().trim()
        dateOfBirth = binding.birthInput.text.toString().trim()
        gender = binding.spGender.selectedItem.toString()
        address = binding.addressInput.text.toString().trim()

        var isError = false

        if(TextUtils.isEmpty(email)){
            binding.emailInput.error = "Please enter your email"
            isError = true
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.error = "Email format is wrong"
            isError = true
        }

        if(TextUtils.isEmpty(password)){
            binding.passInput.error = "Please enter your password"
            isError = true
        }
        else if(password.length < 6){
            binding.passInput.error = "Password at least has 7 character"
            isError = true
        }

        if(TextUtils.isEmpty(username)){
            binding.usernameInput.error = "Please enter your username"
            isError = true
        }

        if(TextUtils.isEmpty(dateOfBirth)){
            dateOfBirth = ""
        }

        if(TextUtils.isEmpty(address)){
            address = ""
        }

        if(!isError){
            firebaseRegister()
        }
    }

    // Membuat akun di firebase berdasarkan input email dan password yang dimasukkan
    private fun firebaseRegister() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Jika register berhasil
                val email = FirebaseAuth.getInstance().currentUser!!.email
                Toast.makeText(this, "Berhasil registrasi menggunakan email $email", Toast.LENGTH_LONG).show()
                saveUserToDatabase()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // Jika register gagal
                Toast.makeText(this, "Gagal melakukan registrasi sebab ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveUserToDatabase() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/users/$uid")

        val user = UserData(uid, email, username, dateOfBirth, gender, address, "")
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "Data $username berhasil disimpan")
            }
            .addOnFailureListener {
                Log.d("Register", "Data $username gagal disimpan")
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