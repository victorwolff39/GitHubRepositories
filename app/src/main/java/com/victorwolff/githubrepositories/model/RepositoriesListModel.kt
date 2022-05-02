package com.victorwolff.githubrepositories.model

import com.google.gson.annotations.SerializedName

data class RepositoriesListModel(
    //@SerializedName("total_count")
    //var total: Int = 0

    @SerializedName("items")
    var items: MutableList<RepositoryModel> = mutableListOf(),

    var isLastPage: Boolean = false
) {

    data class RepositoryModel(
        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("name")
        var name: String = "",

        @SerializedName("stargazers_count")
        var stars: Int = 0,

        @SerializedName("owner")
        var owner: OwnerModel = OwnerModel()

    )
}
