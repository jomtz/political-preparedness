package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionItemBinding
import com.example.android.politicalpreparedness.network.models.Election
import java.nio.file.attribute.FileTime.from

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election,
        ElectionViewHolder>(ElectionDiffCallback()) {

    private var elections = listOf<Election>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount(): Int = elections.size

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.election = elections[position]
            it.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        val binding = ElectionItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ElectionViewHolder(binding)
    }
}

// Create ElectionViewHolder
class ElectionViewHolder(val viewDataBinding: ElectionItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
}

// Create ElectionDiffCallback
class ElectionDiffCallback() : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

}
// Create ElectionListener
class ElectionListener(val block: (Election) -> Unit) {
    fun onClick(election: Election) = block(election)
}