package com.yara.kinoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yara.kinoapp.data.dao.FilmDao
import com.yara.kinoapp.data.entity.FilmApi

@Database(entities = [FilmApi::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}