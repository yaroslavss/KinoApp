package com.yara.kinoapp.data.entity

import com.google.gson.annotations.SerializedName

data class OmdbResults(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val omdbFilms: List<OmdbFilm>,
    @SerializedName("totalResults")
    val totalResults: String
)