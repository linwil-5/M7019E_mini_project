package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentServerItemBinding
import com.example.test.model.Downdetector

class DowndetectorListAdapter(private val downdetectorClickListener: DowndetectorClickListener) :
    ListAdapter<Downdetector, DowndetectorListAdapter.ViewHolder>(DowndetectorListDiffCallback()) {

    class ViewHolder(private var binding: FragmentServerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(downdetector: Downdetector, downdetectorClickListener: DowndetectorClickListener) {

            binding.downdetector = downdetector

            if (downdetector.reachable) {
                binding.serverItemB.visibility = View.GONE
            } else {
                binding.serverItemB.visibility = View.VISIBLE
            }
            binding.clickListener = downdetectorClickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentServerItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), downdetectorClickListener)
    }
}

// Making sure each item are not the same
class DowndetectorListDiffCallback : DiffUtil.ItemCallback<Downdetector>() {
    override fun areItemsTheSame(oldItem: Downdetector, newItem: Downdetector): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Downdetector, newItem: Downdetector): Boolean {
        return oldItem == newItem
    }

}

class DowndetectorClickListener(val clickListener: (downdetector: Downdetector) -> Unit) {
    fun onClick(downdetector: Downdetector) = clickListener(downdetector)
}