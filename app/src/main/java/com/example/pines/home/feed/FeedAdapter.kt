package com.example.pines.home.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pines.core.model.Pines
import com.example.pines.databinding.ItemPinBinding

class FeedAdapter(
    private val onItemClick: (Pines) -> Unit = {}
) : ListAdapter<Pines, FeedAdapter.PinesViewHolder>(DIFF) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            p1: Int
        ): PinesViewHolder {
            val binding = ItemPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PinesViewHolder(binding)
        }

        override fun onBindViewHolder(
            holder: PinesViewHolder,
            position: Int
        ) {
            holder.bind(getItem(position))
        }

        inner class PinesViewHolder(
            private val binding: ItemPinBinding
        ): RecyclerView.ViewHolder(binding.root) {
            fun bind(pines: Pines) {
                binding.tvTitle.text = pines.alternativeSlugs.en

                Glide.with(binding.ivPhoto)
                    .load(pines.urls.regular)
                    .centerCrop()
                    .into(binding.ivPhoto)

                binding.root.setOnClickListener {
                    onItemClick(pines)
                }
            }
        }

        companion object {
            private val DIFF = object: DiffUtil.ItemCallback<Pines>() {
                override fun areItemsTheSame(oldItem: Pines, newItem: Pines) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Pines, newItem: Pines) =
                    oldItem == newItem
            }
        }
    }