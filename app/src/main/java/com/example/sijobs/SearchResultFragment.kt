package com.example.sijobs

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sijobs.databinding.FragmentProfileBinding
import com.example.sijobs.databinding.FragmentSearchBinding
import com.example.sijobs.databinding.FragmentSearchResultBinding
import java.security.acl.Group

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var jobs = mutableListOf(
            Search("Koki"),
            Search("Polisi"),
            Search("Tukang Kebun"),
            Search("Kuli"),
            Search("Dokter"),
            Search("Perawat"),
            Search("Buruh"),
        )

        val adapter = SearchAdapter(jobs)
        binding.rvSearch.adapter = adapter
        binding.etSearch.bringToFront()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}