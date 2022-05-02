package com.victorwolff.githubrepositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorwolff.githubrepositories.databinding.ItemRepositoryBinding
import com.victorwolff.githubrepositories.diffutils.RepositoryDiffUtil
import com.victorwolff.githubrepositories.model.RepositoriesListModel

class RepositoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var oldItems = RepositoriesListModel()

    class RepositoryHolder(itemView: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: RepositoriesListModel.RepositoryModel) {
            binding.repositoryNameText.text = data.name
            binding.starsQuantity.text = data.stars.toString()
            binding.ownerNameText.text = data.owner.name
            Glide.with(binding.root).load(data.owner.avatarUrl)
                .into(binding.ownerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepositoryHolder).setData(oldItems.items[position])
    }

    override fun getItemCount(): Int {
        return oldItems.items.size
    }

    // Clean all elements of the recycler
    fun clear() {
        oldItems.items.clear()
        notifyDataSetChanged()
    }

    fun setData(newList: List<RepositoriesListModel.RepositoryModel>, rv: RecyclerView) {
        val repositoryDiff = RepositoryDiffUtil(oldItems.items, newList)
        val diff = DiffUtil.calculateDiff(repositoryDiff)
        oldItems.items.addAll(newList)
        diff.dispatchUpdatesTo(this)
        rv.scrollToPosition(oldItems.items.size - newList.size)
    }

}