package com.example.sijobs

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.sijobs.databinding.SearchResultBinding
import kotlin.math.roundToInt

class SearchAdapter (
    var dataSearch: List<Search>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: SearchResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.apply {
            tvJob.text = dataSearch[position].job
            Log.d("Search", this.cardContainer.marginBottom.toString())

            if(position == dataSearch.size-1) {
                val layoutParams = this.cardContainer.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.bottomMargin = 230
            }

            if(position == 0) {
                val layoutParams = this.cardContainer.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.topMargin = 200
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSearch.size
    }
}