package com.example.sijobs

import android.app.DatePickerDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sijobs.databinding.ActivityNewProfileBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class NewProfileActivity : AppCompatActivity() {

    lateinit var binding :ActivityNewProfileBinding

    // Menyimpan Uri gambar user
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Simpan foto pengguna
        binding.addImage.setOnClickListener { getImage.launch("image/*") }

        // Validasi perubahan data
        binding.btnSave.setOnClickListener { validateUserData() }

        // Buka input kalender
        binding.etDateofBirth.setOnClickListener { datePicker() }
    }

    private fun validateUserData() {
        // bagian ini ditambahkan validasi data inputan user

        // Jika sudah benar maka update data
        updateUserData()
    }

    private fun updateUserData() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                Log.d("NewProfileActivity", "Gambar berhasil di upload: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("NewProfileActivity", "Gambar dapat di download melalui link: $it")
                }
            }
            .addOnFailureListener{
                Log.d("NewProfileActivity", "Gambar gagal di upload sebab: ${it.message}")
            }
    }

    // Callback request image dari galeri
    val getImage = registerForActivityResult(
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
            binding.etDateofBirth.setText("$mDay/$mMonth/$mYear")
        },year,month, day)
        dpd.show();
    }
}
