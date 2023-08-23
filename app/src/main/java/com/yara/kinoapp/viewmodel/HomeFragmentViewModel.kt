package com.yara.kinoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.yara.kinoapp.App
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val filmsListData: Observable<List<Film>>
    val showProgressBar: BehaviorSubject<Boolean>

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