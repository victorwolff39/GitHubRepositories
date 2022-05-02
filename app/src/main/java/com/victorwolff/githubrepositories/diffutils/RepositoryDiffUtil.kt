package com.victorwolff.githubrepositories.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.victorwolff.githubrepositories.model.RepositoriesListModel

class RepositoryDiffUtil(
    private val oldList: List<RepositoriesListModel.RepositoryModel>,
    private val newList: List<RepositoriesListModel.RepositoryModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepository = oldList[oldItemPosition]
        val newRepository = newList[newItemPosition]
        return oldRepository.id == newRepository.id
    }

}