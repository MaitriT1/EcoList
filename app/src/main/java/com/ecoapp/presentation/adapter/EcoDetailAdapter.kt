package com.ecoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecoapp.databinding.ItemEcoDetailBinding
import com.ecoapp.data.model.EcoDetail


class EcoDetailAdapter(private var items: List<EcoDetail>) :
    RecyclerView.Adapter<EcoDetailAdapter.EcoDeatilViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoDeatilViewHolder {
        val binding = ItemEcoDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EcoDeatilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EcoDeatilViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class EcoDeatilViewHolder(private val binding: ItemEcoDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EcoDetail) {
            with(binding) {
                ivItem.setImageResource(item.imageResId)
                tvTitle.text = item.title
                tvSubtitle.text = item.subTitle
            }
        }
    }

    fun updateItems(newItems: List<EcoDetail>) {
        items = newItems
        notifyDataSetChanged()

    }
}