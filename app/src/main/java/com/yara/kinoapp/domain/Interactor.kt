package com.yara.kinoapp.domain

import com.yara.kinoapp.data.API
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.data.entity.OmdbResults
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.utils.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class Interactor(private val repo: MainRepository, private val retrofitService: OmdbApi, private val preferences: PreferenceProvider) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState = Channel<Boolean>(Channel.CONFLATED)

    fun getFilmsFromApi(page: Int) {
        // set ProgressBar on
        scope.launch {
            progressBarState.send(true)
        }

        retrofitService.getFilms(getDefaultSearchFromPreferences(), API.KEY, page).enqueue(object :
            Callback<OmdbResults> {
            override fun onResponse(call: Call<OmdbResults>, response: Response<OmdbResults>) {
                val listToSave = Converter.convertAPIListToDBList(response.body()?.omdbFilms)
                // on success save fimls into DB and set ProgressBar off
                scope.launch {
                    repo.putToDb(listToSave)
                    progressBarState.send(false)
                }
            }

            override fun onFailure(call: Call<OmdbResults>, t: Throwable) {
                // set ProgressBar off
                scope.launch {
                    progressBarState.send(false)
                }
            }
        })
    }

    // save settings into SharedPreferences
    fun saveDefaultSearchToPreferences(search: String) {
        preferences.saveDefaultSearch(search)
    }

    // get settings from SharedPreferences
    fun getDefaultSearchFromPreferences() = preferences.getDefaultSearch()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB().map { data ->
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