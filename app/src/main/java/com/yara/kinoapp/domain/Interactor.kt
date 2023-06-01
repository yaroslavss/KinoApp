package com.yara.kinoapp.domain

import com.yara.kinoapp.data.*
import com.yara.kinoapp.data.entity.OmdbResults
import com.yara.kinoapp.utils.Converter
import com.yara.kinoapp.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: OmdbApi) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(ApiConstants.DEFAULT_SEARCH, API.KEY, page).enqueue(object :
            Callback<OmdbResults> {
            override fun onResponse(call: Call<OmdbResults>, response: Response<OmdbResults>) {
                callback.onSuccess(Converter.convertApiListToDTOList(response.body()?.omdbFilms))
            }

            override fun onFailure(call: Call<OmdbResults>, t: Throwable) {
                callback.onFailure()
            }
        })
    }
}