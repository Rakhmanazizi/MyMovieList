package com.example.mymovielist.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.repository.MovieRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val mMovieRepository: MovieRepository = MovieRepository(application)
    val allMoviesFavorie: LiveData<List<MovieModel>>

    init {
        allMoviesFavorie = mMovieRepository.getAll()
    }
}

