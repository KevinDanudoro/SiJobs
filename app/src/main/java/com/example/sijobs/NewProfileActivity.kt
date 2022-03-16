package com.example.sijobs

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.sijobs.databinding.ActivityNewProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class NewProfileActivity : AppCompatActivity() {

    lateinit var binding :ActivityNewProfileBinding

    // Data user
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var dateOfBirth: String
    private lateinit var gender: String
    private lateinit var address: String
    private lateinit var firebaseImageProfile: String

    // Directory file image profile user
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Masukkan data lama ke editText
        loadOldDataUserToEditText()

        // Simpan foto pengguna
        binding.addImage.setOnClickListener { getImage.launch("image/*") }

        // Validasi perubahan data
        binding.btnSave.setOnClickListener { validateUserData() }

        // Buka input kalender
        binding.etDateofBirth.setOnClickListener { datePicker() }
    }

    private fun loadOldDataUserToEditText() {
        name = intent.getStringExtra("NAME").toString()
        email = intent.getStringExtra("EMAIL").toString()
        gender = intent.getStringExtra("GENDER").toString()
        address = intent.getStringExtra("ADDRESS").toString()
        dateOfBirth = intent.getStringExtra("DATEOFBIRTH").toString()
        firebaseImageProfile = intent.getStringExtra("IMAGEURL").toString()

        binding.etName.setText(name)
        binding.etEmail.setText(email)
        binding.spGender.setSelection(if(gender == "Male") 0 else 1)
        binding.etAddress.setText(address)
        binding.etDateofBirth.setText(dateOfBirth)
        if(firebaseImageProfile != "null") Picasso.get().load(firebaseImageProfile).into(binding.profileImage)
    }

    private fun validateUserData() {
        // bagian ini ditambahkan validasi data inputan user

        email = binding.etEmail.text.toString().trim()
        name = binding.etName.text.toString().trim()
        dateOfBirth = binding.etDateofBirth.text.toString().trim()
        gender = binding.spGender.selectedItem.toString()
        address = binding.etAddress.text.toString().trim()

        var isError = false

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email format is wrong"
            isError = true
        }

        // Jika sudah benar maka update data
        if(!isError) saveImageToStorage()
    }

    private fun saveImageToStorage() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(imageUri?: "".toUri())
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    firebaseImageProfile = it.toString()
                    updateUserToDatabase()
                }
            }
            .addOnFailureListener{
                Log.d("NewProfileActivity", "Gambar gagal di upload sebab: ${it.message}")
                updateUserToDatabase()
            }
    }

    private fun updateUserToDatabase() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/users/$uid")

        val user = mapOf<String, String>(
            "address" to address,
            "dateOfBirth" to dateOfBirth,
            "email" to email,
            "gender" to gender,
            "imageUrl" to firebaseImageProfile,
            "uid" to uid,
            "name" to name
        )

        ref.updateChildren(user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // Callback request image dari galeri
    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            binding.profileImage.setImageURI(it)
        }
    )

    // Fungsi input kalender
    private fun datePicker(){
        //Calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
            //set to edittext
            var stringDay = mDay.toString();
            var stringMonth = (mMonth+1).toString();
            var stringYear = mYear.toString();

            if(mDay < 10) stringDay = "0$stringDay"
            if(mMonth < 10) stringMonth = "0$stringMonth"

            dateOfBirth = "$stringDay/$stringMonth/$stringYear"
            binding.etDateofBirth.setText(dateOfBirth)

        },year,month, day)
        dpd.show();
    }
}
