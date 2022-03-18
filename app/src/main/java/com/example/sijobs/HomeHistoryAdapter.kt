package com.example.sijobs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sijobs.databinding.ItemHomehistoryBinding

class HomeHistoryAdapter(
    var histories: List<HomeHistory>
) : RecyclerView.Adapter<HomeHistoryAdapter.HistoryViewHolder>()  {

    inner class HistoryViewHolder(val binding: ItemHomehistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHomehistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            tvNamehistory.text = histories[position].name
            tvHisJob.text = histories[position].job
            tvTime.text = histories[position].date
        }
    }

    override fun getItemCount(): Int {
        return histories.size
    }
}