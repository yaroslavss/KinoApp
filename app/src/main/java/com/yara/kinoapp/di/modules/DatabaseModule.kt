package com.yara.kinoapp.di.modules

import com.yara.kinoapp.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun ProvideRepository() = MainRepository()
}