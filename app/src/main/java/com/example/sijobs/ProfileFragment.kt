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

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var age: String
    private lateinit var gender: String
    private lateinit var address: String
    private lateinit var imageUrl: String

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
            startActivity(Intent(this.requireContext(), NewProfileActivity::class.java))
        }

        binding.logout.setOnClickListener {
            userLogout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun readData() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/users/$uid")
        ref.get()
            .addOnSuccessListener {
                name = it.child("username").value.toString()
                email = it.child("email").value.toString()
                gender = it.child("gender").value.toString()
                address = it.child("address").value.toString()
                imageUrl = it.child("imageurl").value.toString()

                // Masih berupa tanggal lahir bukan usia
                //age = it.child("dateOfBirth").value.toString()

                bindingDataFromDatabase()
            }
    }

    private fun bindingDataFromDatabase() {
        // name masih merupakan username
        binding.tvName.setText(": $name")
        binding.tvEmail.setText(": $email")
        binding.tvGender.setText(": $gender")
        binding.tvAddress.setText(": $address")

        // Belom bisa
        // binding.tvAge.text = age
    }

    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this.requireContext(), LoginActivity::class.java))
        getActivity()?.finish()
    }


}
