package com.yara.kinoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.yara.kinoapp.App
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.domain.Interactor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val filmsListData: Flow<List<Film>>
    val showProgressBar: Channel<Boolean>

    // init interactor
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }
}