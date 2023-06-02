package com.yara.kinoapp.data

import com.yara.kinoapp.data.entity.OmdbResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    fun getFilms(
        @Query("s") s: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: Int
    ): Call<OmdbResults>
}