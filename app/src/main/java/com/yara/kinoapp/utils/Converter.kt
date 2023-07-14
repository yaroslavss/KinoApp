package com.yara.kinoapp.utils

import com.yara.kinoapp.data.entity.FilmApi
import com.yara.kinoapp.data.entity.OmdbFilm
import com.yara.kinoapp.domain.Film
import kotlin.random.Random

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

    // for sending to view (from API)
    fun convertAPIListToDTOList(list: List<OmdbFilm>?): List<Film> {
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
    }

    // for sending to view (from DB)
    fun convertDBListToDTOList(list: List<FilmApi>?): List<Film> {
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
    }
}