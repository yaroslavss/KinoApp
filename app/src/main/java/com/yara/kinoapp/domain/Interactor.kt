package com.yara.kinoapp.domain

import com.yara.kinoapp.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}