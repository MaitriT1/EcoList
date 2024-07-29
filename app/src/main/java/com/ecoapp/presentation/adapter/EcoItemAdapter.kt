package com.ecoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecoapp.databinding.ItemEcoBinding
import com.ecoapp.data.model.EcoItem

class EcoItemAdapter(private var items: List<EcoItem>) : RecyclerView.Adapter<EcoItemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEcoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEcoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carouselItem = items[position]
        holder.binding.ivCarousel.setImageResource(carouselItem.imageResId)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<EcoItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
