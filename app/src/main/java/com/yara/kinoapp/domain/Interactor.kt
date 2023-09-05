package com.yara.kinoapp.domain

import com.yara.kinoapp.data.API
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.utils.Converter
import com.yara.remote_module.OmdbApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlin.random.Random

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: OmdbApi,
    private val preferences: PreferenceProvider
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        // set ProgressBar on
        progressBarState.onNext(true)

        retrofitService.getFilms(getDefaultSearchFromPreferences(), API.KEY, page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertAPIListToDBList(it.omdbFilms)
            }
            .subscribeBy(
                onError = {
                    // on error set ProgressBar off
                    progressBarState.onNext(false)
                },
                onNext = {
                    // on success save films into DB and set ProgressBar off
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
    }

    fun getSearchResultFromApi(search: String): Observable<List<Film>> =
        retrofitService.getFilmsFromSearch(search, API.KEY, 1)
            .map {
                Converter.convertAPIListToDTOList(it.omdbFilms)
            }

    // save settings into SharedPreferences
    fun saveDefaultSearchToPreferences(search: String) {
        preferences.saveDefaultSearch(search)
    }

    // get settings from SharedPreferences
    fun getDefaultSearchFromPreferences() = preferences.getDefaultSearch()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB().map { data ->
        // convert DB (API) data to DTO data with map operator
        data.map {
            Film(
                title = it.title,
                poster = it.poster,
                description = "IMDB ID: ${it.imdbID}\r\nType: ${it.type}\r\nYear: ${it.year}",
                rating = Random.nextDouble(3.0, 10.0),
                isInFavorites = false
            )
        }
    }

    fun clearDB() {
        repo.clearDB()
    }
}