package com.example.sijobs

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sijobs.databinding.FragmentAddSkillBinding
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.listOf as listOf1

class AddSkillFragment : Fragment(R.layout.fragment_add_skill) {

    // Binding deklarasi
    private lateinit var binding: FragmentAddSkillBinding

    // Binding firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding init
        binding = FragmentAddSkillBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inisialisasi firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/")

        // Menambah skill user
        addSkill()

    }

    private fun addSkill() {
        val listSkill = mutableListOf(
            binding.item1,
            binding.item2,
            binding.item3,
            binding.item4,
            binding.item5,
            binding.item6,
            binding.item7,
            binding.item8,
            binding.item9,
            binding.item10,
            binding.item11,
            binding.item12,
            binding.item13,
            binding.item14,
        )

        var addedSkill = mutableMapOf<String, String>()

        // Agar dapat lebih mudah diolah
        val uid = firebaseAuth.currentUser!!.uid
        addedSkill["uid"] = uid

        listSkill.forEach {
            it.setOnClickListener { _ ->

                if(addedSkill.size <= 3 && !it.isSelected){
                    it.isSelected = true
                    addedSkill[it.text.toString()] = it.text.toString()
                }
                else if(addedSkill.size > 1 && it.isSelected){
                    it.isSelected = false
                    addedSkill.remove(it.text.toString())
                }
                else
                    Toast.makeText(this.requireContext(), "Number of skill has reach limit", Toast.LENGTH_LONG).show()
            }
        }

        addDataToDatabase(addedSkill, uid)
    }

    private fun addDataToDatabase(skills: Map<String, String>, uid: String) {
        binding.saveBtn.setOnClickListener {
            val usersRef = firebaseDatabase.getReference("/jobs/$uid")

            usersRef.setValue(skills)
                .addOnSuccessListener {
                    Log.d("AddSkill", "YEEEYYYY")

                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fl_Fragmment, ProfileFragment())
                    transaction.commit()
                }
                .addOnFailureListener {
                    Log.d("AddSkill", "NOOOOOO")
                }
        }
    }

}