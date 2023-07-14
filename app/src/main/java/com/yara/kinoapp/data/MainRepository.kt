package com.yara.kinoapp.data

import com.yara.kinoapp.data.dao.FilmDao
import com.yara.kinoapp.data.entity.FilmApi
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {
    // insert data into DB
    fun putToDb(films: List<FilmApi>) {
        // run DB queries in new thread
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    // select all data from DB
    fun getAllFromDB(): List<FilmApi> {
        return filmDao.getCachedFilms()
    }

    // delete all data from DB
    fun clearDB() {
        Executors.newSingleThreadExecutor().execute {
            filmDao.deleteAll()
        }
    }
}