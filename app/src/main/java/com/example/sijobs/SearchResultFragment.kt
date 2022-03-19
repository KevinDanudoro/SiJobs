package com.example.sijobs

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.sijobs.databinding.FragmentSearchResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.security.Key

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance("https://si-jobs-b923c-default-rtdb.asia-southeast1.firebasedatabase.app/")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.etSearch.bringToFront()
        bindingJobsDataFromDatabase("")

        binding.etSearch.setOnKeyListener(View.OnKeyListener{view, keycode, event ->
            if(keycode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                bindingJobsDataFromDatabase(binding.etSearch.text.toString())
                return@OnKeyListener true
            }
            false
        })
    }

    private fun bindingJobsDataFromDatabase(input: String) {
        var rvJobs = mutableListOf<Search>()
        val pattern = Regex(input.lowercase())

        val ref = firebaseDatabase.getReference("/jobs")
        ref.get()
            .addOnSuccessListener { allUsersJob ->
                allUsersJob.children.forEach { usersJob ->
                    if (usersJob.child("uid").value.toString() != firebaseAuth.currentUser!!.uid){
                        usersJob.children.forEach {
                            if (it.value.toString() != usersJob.child("uid").value.toString() && pattern.containsMatchIn(it.value.toString().lowercase()))
                                rvJobs.add(Search(it.value.toString()))
                        }
                    }
                }

                val adapter = SearchAdapter(rvJobs)
                binding.rvSearch.adapter = adapter
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
