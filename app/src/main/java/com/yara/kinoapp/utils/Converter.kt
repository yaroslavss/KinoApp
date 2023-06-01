package com.yara.kinoapp.utils

import com.yara.kinoapp.data.entity.OmdbFilm
import com.yara.kinoapp.domain.Film
import kotlin.random.Random

object Converter {
    fun convertApiListToDTOList(list: List<OmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.poster,
                description = "IMDB ID: ${it.imdbID}\r\nType: ${it.type}\r\nYear: ${it.year}",
                rating = Random.nextDouble(3.0,10.0),
                isInFavorites = false
            ))
        }
        return result
    }}