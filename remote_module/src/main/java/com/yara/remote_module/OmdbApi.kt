package com.yara.remote_module

import com.yara.remote_module.entity.OmdbResults
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    fun getFilms(
        @Query("s") s: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: Int
    ): Observable<OmdbResults>

    @GET("/")
    fun getFilmsFromSearch(
        @Query("s") s: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: Int
    ): Observable<OmdbResults>
}