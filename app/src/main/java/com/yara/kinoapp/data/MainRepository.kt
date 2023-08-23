package com.yara.kinoapp.data

import com.yara.kinoapp.data.dao.FilmDao
import com.yara.kinoapp.data.entity.FilmApi
import io.reactivex.rxjava3.core.Observable

class MainRepository(private val filmDao: FilmDao) {
    // insert data into DB
    fun putToDb(films: List<FilmApi>) {
        filmDao.insertAll(films)
    }

    // select all data from DB
    fun getAllFromDB(): Observable<List<FilmApi>> {
        return filmDao.getCachedFilms()
    }

    // delete all data from DB
    fun clearDB() {
        filmDao.deleteAll()
    }
}