package com.yara.kinoapp.domain

import com.yara.kinoapp.data.API
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.data.entity.OmdbResults
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.utils.Converter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        retrofitService.getFilms(getDefaultSearchFromPreferences(), API.KEY, page).enqueue(object :
            Callback<OmdbResults> {
            override fun onResponse(call: Call<OmdbResults>, response: Response<OmdbResults>) {
                val listToSave = Converter.convertAPIListToDBList(response.body()?.omdbFilms)
                // on success save films into DB and set ProgressBar off
                Completable.fromSingle<List<Film>> {
                    repo.putToDb(listToSave)
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                progressBarState.onNext(false)
            }

            override fun onFailure(call: Call<OmdbResults>, t: Throwable) {
                // set ProgressBar off
                progressBarState.onNext(false)
            }
        })
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