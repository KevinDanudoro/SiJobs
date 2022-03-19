package com.example.sijobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sijobs.databinding.FragmentHomeBinding
import com.example.sijobs.databinding.FragmentSearchResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/")

        loadUserDataFromDatabase()
    }

    private fun loadUserDataFromDatabase() {
        val uid = firebaseAuth.currentUser!!.uid
        val ref = firebaseDatabase.getReference("/users/$uid")
        ref.get()
            .addOnSuccessListener {
                val imageUrl = it.child("imageUrl").value.toString()
                val name = it.child("name").value.toString()

                bindingUserData(name, imageUrl)
            }
    }

    private fun bindingUserData(name: String, imageUrl: String) {
        binding.tvName.text = "Hey, $name"
        if(imageUrl != "null")
            Picasso.get().load(imageUrl).into(binding.tvProfilePic)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}