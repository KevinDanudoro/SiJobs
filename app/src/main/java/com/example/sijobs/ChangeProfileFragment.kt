package com.example.sijobs

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.sijobs.databinding.FragmentChangeProfileBinding
import com.example.sijobs.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class ChangeProfileFragment : Fragment(R.layout.fragment_change_profile) {

    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    // Data user
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var firebaseImageProfile: String

    // Directory file image profile user
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.ivBackArrow.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_Fragmment, ProfileFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

        loadUsersDataFromDatabase()

        // Simpan foto pengguna
        binding.addImage.setOnClickListener { getImage.launch("image/*") }

        // Validasi perubahan data
        binding.btnSave.setOnClickListener { validateUserData() }

    }

    private fun loadUsersDataFromDatabase() {
        val uid = firebaseAuth.currentUser!!.uid
        val ref = firebaseDatabase.getReference("/users/$uid")
        ref.get()
            .addOnSuccessListener {
                name = it.child("name").value.toString()
                firebaseImageProfile = it.child("imageUrl").value.toString()
                address = it.child("address").value.toString()


                binding.etName.setText(name)
                binding.etAddress.setText(address)
                if(firebaseImageProfile != "null") Picasso.get().load(firebaseImageProfile).into(binding.profileImage)
            }
    }

    private fun validateUserData() {
        name = binding.etName.text.toString().trim()
        address = binding.etAddress.text.toString().trim()

        saveImageToStorage()
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
            "imageUrl" to firebaseImageProfile,
            "uid" to uid,
            "name" to name
        )

        ref.updateChildren(user)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_Fragmment, ProfileFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    // Callback request image dari galeri
    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            imageUri = it
            binding.profileImage.setImageURI(it)
        }
    )

}