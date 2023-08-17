package com.yara.kinoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yara.kinoapp.data.entity.FilmApi
import io.reactivex.rxjava3.core.Observable

@Dao
interface FilmDao {
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): Observable<List<FilmApi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<FilmApi>)

    @Query("DELETE FROM cached_films")
    fun deleteAll()
}