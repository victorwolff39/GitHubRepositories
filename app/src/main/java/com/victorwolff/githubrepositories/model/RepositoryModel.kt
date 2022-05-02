package com.victorwolff.githubrepositories.model

import com.google.gson.annotations.SerializedName

class RepositoryModel {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String = ""

    @SerializedName("stargazers_count")
    var stars: Int = 0

    @SerializedName("owner")
    var owner: OwnerModel = OwnerModel()

}