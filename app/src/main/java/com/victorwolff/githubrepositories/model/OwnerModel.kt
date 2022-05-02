package com.victorwolff.githubrepositories.model

import com.google.gson.annotations.SerializedName

class OwnerModel {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("login")
    var login: String = ""

    @SerializedName("avatar_url")
    var avatarUrl: String = ""

    @SerializedName("url")
    var url: String = ""

    var name: String = ""

}