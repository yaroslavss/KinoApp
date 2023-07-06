package com.yara.kinoapp.data

import android.content.ContentValues
import android.database.Cursor
import com.yara.kinoapp.data.db.DatabaseHelper
import com.yara.kinoapp.domain.Film

class MainRepository(databaseHelper: DatabaseHelper) {
    private val sqlDb = databaseHelper.readableDatabase
    // cursor for fetching data from DB
    private lateinit var cursor: Cursor

    // insert data into DB
    fun putToDb(film: Film) {
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
        }

        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    // select all data from DB
    fun getAllFromDB(): List<Film> {
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        val result = mutableListOf<Film>()

        if (cursor.moveToFirst()) {
            // if cursor not empty
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)

                result.add(Film(title, poster, description, rating))
            } while (cursor.moveToNext())
        }

        return result
    }
}