package com.example.mymovielist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mymovielist.database.MovieModelRoom
import com.example.mymovielist.database.MovieRoomDatabase
import com.example.mymovielist.database.MovieDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(application: Application) {
    private val mMovieDao : MovieDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MovieRoomDatabase.getDatabase(application)
        mMovieDao = db.movieDao()
    }

    fun getAllMovie() : LiveData<List<MovieModelRoom>> = mMovieDao.getAllMember()

    fun insert(movie: MovieModelRoom) {
        executorService.execute { mMovieDao.insert(movie) }
    }

    fun delete(movie: MovieModelRoom) {
        executorService.execute { mMovieDao.delete(movie) }
    }
}