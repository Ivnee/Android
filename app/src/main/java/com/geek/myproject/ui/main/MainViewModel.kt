package com.geek.myproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geek.myproject.data.NotesRepository

class MainViewModel:ViewModel() {
    private val viewStateLiveData :MutableLiveData<MainViewState> = MutableLiveData()//создаем лайв дату
    init{
        viewStateLiveData.value = MainViewState(NotesRepository.notes)//сразу сетим в подписчиков на лайв дату наши данные из репозитория
    }
    fun viewState():LiveData<MainViewState> = viewStateLiveData//метод return нашей лайв даты

}