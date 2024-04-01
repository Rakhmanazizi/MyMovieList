package com.example.mymovielist.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mymovielist.database.MovieModelRoom
import com.example.mymovielist.repository.MovieRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val mMovieRepository : MovieRepository = MovieRepository(application)

    fun insert(movie: MovieModelRoom) {
        mMovieRepository.insert(movie)
    }

    fun delete(movie: MovieModelRoom) {
        mMovieRepository.delete(movie)
    }
}

