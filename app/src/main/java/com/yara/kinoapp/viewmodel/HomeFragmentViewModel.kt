package com.yara.kinoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yara.kinoapp.App
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: LiveData<List<Film>>
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    // init interactor
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)

        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess() {
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                showProgressBar.postValue(false)
            }
        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}