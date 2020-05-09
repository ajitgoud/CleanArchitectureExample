package com.android.developer.cleanapparchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.cleanapparchitecture.databinding.ItemMarsPropertyBinding
import com.android.developer.cleanapparchitecture.domain.MarsProperty

class MarsRecyclerAdapter : ListAdapter<MarsProperty, MarsRecyclerAdapter.MarsPropertyViewHolder>(MarsDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MarsPropertyViewHolder private constructor(private var binding: ItemMarsPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.marsProperty = marsProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMarsPropertyBinding.inflate(layoutInflater, parent, false)
                return MarsPropertyViewHolder(binding)
            }
        }
    }


    internal object MarsDiffUtil : DiffUtil.ItemCallback<MarsProperty>() {

        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

    }

}