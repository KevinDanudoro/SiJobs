package com.example.sijobs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sijobs.databinding.FragmentAddSkillBinding
import com.example.sijobs.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var age: String
    private lateinit var gender: String
    private lateinit var address: String
    private lateinit var imageUrl: String
    private lateinit var dateOfBirth: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readData();

        binding.changeProfile.setOnClickListener {
            sendDataToNewProfile()
        }

        binding.logout.setOnClickListener {
            userLogout()
        }
    }

    // Agar tidak terjadi memory leak
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun sendDataToNewProfile() {
        Intent(this.requireContext(), NewProfileActivity::class.java).also {
            it.putExtra("NAME", name)
            it.putExtra("EMAIL", email)
            it.putExtra("GENDER", gender)
            it.putExtra("ADDRESS", address)
            it.putExtra("IMAGEURL", imageUrl)
            it.putExtra("DATEOFBIRTH", dateOfBirth)

            // TODO: Gambar belum bisa di kirim lewat intent. Cari cara!!
            // it.putExtra("IMAGEURL", imageUrl)

            startActivity(it)
        }
    }

    private fun readData() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/users/$uid")
        ref.get()
            .addOnSuccessListener {
                username = it.child("username").value.toString()
                name = it.child("name").value.toString()
                email = it.child("email").value.toString()
                gender = it.child("gender").value.toString()
                address = it.child("address").value.toString()
                imageUrl = it.child("imageUrl").value.toString()
                dateOfBirth = it.child("dateOfBirth").value.toString()

                val Birth = dateOfBirth.split("/").toTypedArray()

                val calendar = Calendar.getInstance()
                val yearNow = calendar.get(Calendar.YEAR)
                val monthNow = calendar.get(Calendar.MONTH)
                val dayNow = calendar.get(Calendar.DAY_OF_MONTH)

                var preAge = (yearNow - Birth[2].toInt()) - 1
                if(Birth[1].toInt() < monthNow) preAge++
                if(Birth[1].toInt() == monthNow && Birth[0].toInt() <= dayNow) preAge++
                age = preAge.toString()

                bindingDataFromDatabase()
            }
    }

    private fun bindingDataFromDatabase() {
        // username masih merupakan userusername
        binding.profileName.text = username
        binding.tvName.text = ": $name"
        binding.tvEmail.text = ": $email"
        binding.tvGender.text = ": $gender"
        binding.tvAddress.text = ": $address"
        binding.tvAge.text = ": $age"
        if(imageUrl != "null") Picasso.get().load(imageUrl).into(binding.profileImage)
    }

    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this.requireContext(), LoginActivity::class.java))
        activity?.finish()
    }

}
