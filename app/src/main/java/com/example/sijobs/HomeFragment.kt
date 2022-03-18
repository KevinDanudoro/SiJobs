package com.example.sijobs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sijobs.databinding.FragmentHomeBinding
import com.example.sijobs.databinding.FragmentSearchResultBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var history = mutableListOf(
            HomeHistory("Ainsley Lisa", "Designer", "17 Februari 2022"),
            HomeHistory("Aether", "Kurir", "15 Februari 2022"),
            HomeHistory("Emmanulle Proulx", "Designer", "14 Februari 2022"),
        )

        val adapter = HomeHistoryAdapter(history)
        binding.rvHistory.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}