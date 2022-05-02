package com.victorwolff.githubrepositories.service

import com.victorwolff.githubrepositories.model.RepositoriesListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoriesService {

    @GET("search/repositories")
    fun list(@Query("q") q: String, @Query("sort") sort: String): Call<RepositoriesListModel>

    @GET("search/repositories")
    fun list(@Query("q") q: String, @Query("sort") sort: String, @Query("page") page: Int): Call<RepositoriesListModel>

}