package com.yara.kinoapp.di.modules

import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, omdbApi: OmdbApi) = Interactor(repo = repository, retrofitService = omdbApi)
}