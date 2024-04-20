package com.example.mymovielist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.crudlocaldb.data.MovieRoomDatabase
import com.example.mymovielist.database.dao.MovieDao
import com.example.mymovielist.database.entity.MovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(application: Application) {
    private val mMovieDao: MovieDao
    private val executorService: ExecutorService =
        Executors.newSingleThreadExecutor()
    init {
        val db = MovieRoomDatabase.getInstance(application)
        mMovieDao = db.movieDao()
    }
    fun getAll(): LiveData<List<MovieModel>> = mMovieDao.getAll()
    fun insertAll(member: MovieModel) {
        executorService.execute { mMovieDao.insertAll(member) }
    }
    fun delete(member: MovieModel) {
        executorService.execute { mMovieDao.delete(member) }
    }

}
