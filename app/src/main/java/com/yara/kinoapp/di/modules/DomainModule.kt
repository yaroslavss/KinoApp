package com.yara.kinoapp.di.modules

import android.content.Context
import com.yara.kinoapp.data.MainRepository
import com.yara.remote_module.OmdbApi
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(
        repository: MainRepository,
        omdbApi: OmdbApi,
        preferenceProvider: PreferenceProvider
    ) = Interactor(repo = repository, retrofitService = omdbApi, preferences = preferenceProvider)
}