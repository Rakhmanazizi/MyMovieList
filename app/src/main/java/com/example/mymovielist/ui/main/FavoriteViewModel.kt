package com.example.mymovielist.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.repository.MovieRepository

class FavoriteViewModel(application: Application) :ViewModel() {
    private val mMovieRepository: MovieRepository = MovieRepository(application)

    fun getAll(): LiveData<List<MovieModel>> = mMovieRepository.getAll()
}