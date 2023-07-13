package com.yara.kinoapp.di.modules

import android.content.Context
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Singleton
    @Provides
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}