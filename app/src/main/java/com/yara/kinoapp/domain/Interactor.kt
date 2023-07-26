package com.yara.kinoapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.yara.kinoapp.data.API
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.data.entity.OmdbResults
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.utils.Converter
import com.yara.kinoapp.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class Interactor(private val repo: MainRepository, private val retrofitService: OmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultSearchFromPreferences(), API.KEY, page).enqueue(object :
            Callback<OmdbResults> {
            override fun onResponse(call: Call<OmdbResults>, response: Response<OmdbResults>) {
                // save to DB
                val listToSave = Converter.convertAPIListToDBList(response.body()?.omdbFilms)
                repo.putToDb(listToSave)
                callback.onSuccess()
            }

            override fun onFailure(call: Call<OmdbResults>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    fun saveDefaultSearchToPreferences(search: String) {
        preferences.saveDefaultSearch(search)
    }

    fun getDefaultSearchFromPreferences() = preferences.getDefaultSearch()

    fun getFilmsFromDB(): LiveData<List<Film>> = Transformations.map(repo.getAllFromDB()) { data ->
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