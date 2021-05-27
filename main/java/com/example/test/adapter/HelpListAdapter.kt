package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentHelpItemBinding
import com.example.test.model.Help

class HelpListAdapter() :
    ListAdapter<Help, HelpListAdapter.ViewHolder>(HelpListDiffCallback()) {

    class ViewHolder(private var binding: FragmentHelpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(help: Help) {

            binding.help = help
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentHelpItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

// Making sure each item are not the same
class HelpListDiffCallback : DiffUtil.ItemCallback<Help>() {
    override fun areItemsTheSame(oldItem: Help, newItem: Help): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Help, newItem: Help): Boolean {
        return oldItem == newItem
    }

}