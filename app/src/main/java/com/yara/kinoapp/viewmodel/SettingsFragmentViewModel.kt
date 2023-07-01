package com.yara.kinoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yara.kinoapp.App
import com.yara.kinoapp.domain.Interactor
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor
    val searchStringLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        getSearchString()
    }

    private fun getSearchString() {
        // put to LiveData
        searchStringLiveData.value = interactor.getDefaultSearchFromPreferences()
    }

    fun putSearchString(searchStr: String) {
        // save to preferences
        interactor.saveDefaultSearchToPreferences(searchStr)
        getSearchString()
    }
}