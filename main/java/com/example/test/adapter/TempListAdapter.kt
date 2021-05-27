package com.example.test.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentTempItemBinding
import com.example.test.model.Temp
import com.example.test.utils.Constants.SCALE_DEGREE_CELCIUS

class TempListAdapter(private val tempClickListener: TempClickListener) :
    ListAdapter<Temp, TempListAdapter.ViewHolder>(TempListDiffCallback()) {

    class ViewHolder(private var binding: FragmentTempItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(temp: Temp, tempClickListener: TempClickListener) {

            val buildString = StringBuilder()
            buildString.append(temp.temp)
            //buildString.append(" °C")

            val valueTemp: Float = temp.temp.toFloat()
            val colorTemp = when (valueTemp) {
                in 0f..5f -> Color.parseColor("#00FFFF")
                in 5f..10f -> Color.parseColor("#00DBFF")
                in 10f..20f -> Color.parseColor("#10A5F5")
                in 20f..35f -> Color.parseColor("#0859C6")
                in 35f..55f -> Color.parseColor("#FFF200")
                else -> Color.parseColor("#FF0000")
            }

            binding.tempTempTv.text = buildString
            binding.tempTempTv.setTextColor(colorTemp)
            binding.temp = temp
            binding.tempScaleTv.text = SCALE_DEGREE_CELCIUS

            binding.clickListener = tempClickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentTempItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), tempClickListener)
    }
}

// Making sure each item are not the same
class TempListDiffCallback : DiffUtil.ItemCallback<Temp>() {
    override fun areItemsTheSame(oldItem: Temp, newItem: Temp): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Temp, newItem: Temp): Boolean {
        return oldItem == newItem
    }

}

class TempClickListener(val clickListener: (temp: Temp) -> Unit) {
    fun onClick(temp: Temp) = clickListener(temp)
}