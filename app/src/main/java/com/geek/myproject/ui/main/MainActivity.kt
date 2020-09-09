package com.geek.myproject.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.geek.myproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    val adapter: NotesRVAdapter = NotesRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)//достаем ViewModel(кэшируем вью модель)
        rv_notes.layoutManager = GridLayoutManager(this,2)
        rv_notes.adapter = adapter
        viewModel.viewState().observe(this, Observer { s->
            s?.let { adapter.notes = s.notes }})//S данные, которые приходят подписчикам от  вью модели
    }
}