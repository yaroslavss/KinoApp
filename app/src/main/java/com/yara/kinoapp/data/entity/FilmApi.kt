package com.yara.kinoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cached_films", indices = [Index(value = ["title"], unique = true)])
data class FilmApi(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "imdb_id") val imdbID: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "year") var year: String
)