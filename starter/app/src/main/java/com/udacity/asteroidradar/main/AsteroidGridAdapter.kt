package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.GridViewItemBinding

class AsteroidGridAdapter(val onClickListener: (asteroid: Asteroid) -> Unit) :
    ListAdapter<Asteroid, AsteroidGridAdapter.AsteroidGridViewHolder>(AsteroidGridDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidGridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AsteroidGridViewHolder(
            GridViewItemBinding.inflate(inflater)
        )
    }

    override fun onBindViewHolder(holder: AsteroidGridViewHolder, position: Int) {
        getItem(position).let { item ->
            holder.itemView.setOnClickListener {
                onClickListener(item)
            }
            holder.bind(item)
        }
    }

    inner class AsteroidGridViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
        }
    }

    private class AsteroidGridDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

        override fun areItemsTheSame(
            oldItem: Asteroid,
            newItem: Asteroid
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Asteroid,
            newItem: Asteroid
        ): Boolean = oldItem.id == newItem.id

    }
}