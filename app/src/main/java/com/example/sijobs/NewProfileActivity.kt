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
import androidx.fragment.app.Fragment
import com.example.sijobs.databinding.ActivityNewProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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
    private lateinit var imageUri: Uri
    private var imageUriAvailabel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Simpan foto pengguna
        binding.addImage.setOnClickListener { getImage.launch("image/*") }

        // Validasi perubahan data
        binding.btnSave.setOnClickListener { validateUserData() }

        // Buka input kalender
        binding.etDateofBirth.setOnClickListener { datePicker() }
    }

    private fun validateUserData() {
        // bagian ini ditambahkan validasi data inputan user

        email = binding.etEmail.text.toString().trim()
        name = binding.etName.text.toString().trim()
        dateOfBirth = binding.etDateofBirth.text.toString().trim()
        gender = binding.spGender.selectedItem.toString()
        address = binding.etAddress.text.toString().trim()

        var isError = false

        // TODO: Tambahkan fitur dimana jika user tidak mengubah data, maka data sebelumnya akan digunakan lagi
        // TODO: Agar tidak terlalu banyak terhubung ke firebase bisa mengirimkan data dari fragment profile ke activity change profile
        // Tapi kalo gambar gimana ngirimnya?

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

        if(imageUriAvailabel){
            ref.putFile(imageUri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        firebaseImageProfile = it.toString()
                        updateUserToDatabase()
                    }
                }
                .addOnFailureListener{
                    Log.d("NewProfileActivity", "Gambar gagal di upload sebab: ${it.message}")
                    firebaseImageProfile = ""
                }
        }
        else{
            firebaseImageProfile = ""
            updateUserToDatabase()
        }

        startActivity(Intent(this, MainActivity::class.java))
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
            "username" to ""
        )

        ref.updateChildren(user)
    }

    // Callback request image dari galeri
    val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            imageUriAvailabel = true
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
            binding.etDateofBirth.setText("$mDay/$mMonth/$mYear")
        },year,month, day)
        dpd.show();
    }
}
