package com.example.mymovielist.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovielist.database.MovieModelRoom
import com.example.mymovielist.repository.MovieRepository

class MainViewModel(application: Application) :ViewModel() {
    private val mMovieRepository : MovieRepository = MovieRepository(application)

    fun getAllMovies(): LiveData<List<MovieModelRoom>> =
        mMovieRepository.getAllMovie()
}