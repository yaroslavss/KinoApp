package com.yara.kinoapp.domain

import com.yara.kinoapp.data.*
import com.yara.kinoapp.data.entity.OmdbResults
import com.yara.kinoapp.data.preference.PreferenceProvider
import com.yara.kinoapp.utils.Converter
import com.yara.kinoapp.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: OmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultSearchFromPreferences(), API.KEY, page).enqueue(object :
            Callback<OmdbResults> {
            override fun onResponse(call: Call<OmdbResults>, response: Response<OmdbResults>) {
                // save to DB
                val listToSave = Converter.convertAPIListToDBList(response.body()?.omdbFilms)
                repo.putToDb(listToSave)

                // return to view
                val listToShow = Converter.convertAPIListToDTOList(response.body()?.omdbFilms)
                callback.onSuccess(listToShow)
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

    fun getFilmsFromDB(): List<Film> = Converter.convertDBListToDTOList(repo.getAllFromDB())

    fun clearDB() {
        repo.clearDB()
    }
}