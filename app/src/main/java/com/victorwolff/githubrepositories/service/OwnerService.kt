package com.victorwolff.githubrepositories.service

import com.victorwolff.githubrepositories.model.OwnerModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OwnerService {

    @GET("users/{user}")
    fun getInfo(@Path("user") user: String): Call<OwnerModel>

    @GET("users/{user}")
    fun getInfo(@Path("user") user: String, @Header("Authorization") authorization: String): Call<OwnerModel>

}