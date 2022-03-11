package com.example.sijobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sijobs.databinding.FragmentAddSkillBinding

class AddSkillFragment : Fragment(R.layout.fragment_add_skill) {

    // Binding deklarasi
    private lateinit var binding: FragmentAddSkillBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding init
        binding = FragmentAddSkillBinding.inflate(layoutInflater)
        return binding.root


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.item1.setOnClickListener {
            Log.d("Fragment", "Berhasil binding")
        }
    }

}