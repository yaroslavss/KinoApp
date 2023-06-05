package com.yara.kinoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.domain.Interactor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeFragmentViewModel : ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()

    // init interactor
    private val interactor: Interactor by inject()

    init {
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}