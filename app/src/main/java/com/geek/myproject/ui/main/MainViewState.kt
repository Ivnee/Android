package com.geek.myproject.ui.main
import com.geek.myproject.data.entity.Note
//просто класс состояния данных,которые мы обрабатываем через liveData
class MainViewState(val notes:List<Note>) //дополнительно оборачиваем данные из репозитория в MainViewState
