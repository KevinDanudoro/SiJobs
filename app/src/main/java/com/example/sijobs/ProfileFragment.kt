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

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeProfile.setOnClickListener {
            startActivity(Intent(this.requireContext(), NewProfileActivity::class.java))
        }

        binding.logout.setOnClickListener {
            userLogout()
        }
    }

    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this.requireContext(), LoginActivity::class.java))
        getActivity()?.finish()
    }

}