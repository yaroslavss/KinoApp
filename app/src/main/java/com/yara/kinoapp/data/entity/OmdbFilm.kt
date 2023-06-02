package com.yara.kinoapp.data.entity

import com.google.gson.annotations.SerializedName

data class OmdbFilm(
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String
)