package com.yara.kinoapp.utils

import com.yara.kinoapp.data.entity.FilmApi
import com.yara.kinoapp.data.entity.OmdbFilm

object Converter {
    // for saving into DB (from API)
    fun convertAPIListToDBList(list: List<OmdbFilm>?): List<FilmApi> {
        val result = mutableListOf<FilmApi>()
        list?.forEach {
            result.add(FilmApi(
                imdbID = it.imdbID,
                title = it.title,
                poster = it.poster,
                type = it.type,
                year = it.year
            ))
        }
        return result
    }
}