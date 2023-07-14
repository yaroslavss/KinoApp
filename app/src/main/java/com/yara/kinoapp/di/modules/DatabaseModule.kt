package com.yara.kinoapp.di.modules

import android.content.Context
import androidx.room.Room
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.dao.FilmDao
import com.yara.kinoapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        ).build().filmDao()

    @Singleton
    @Provides
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}