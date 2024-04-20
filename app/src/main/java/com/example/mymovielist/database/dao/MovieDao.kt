package com.example.mymovielist.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM moviemodel")
    fun getAll(): LiveData<List<MovieModel>>

    @Insert
    fun insertAll(vararg movieModel: MovieModel)

    @Delete
    fun delete(movieModel: MovieModel)

    @Query("SELECT * FROM moviemodel WHERE id = :id")
    fun get(id: Int): MovieModel

    @Query("SELECT * FROM moviemodel WHERE judul = :judul")
    fun getMovieByTitle(judul: String): MovieModel

    @Query("DELETE FROM moviemodel WHERE judul = :judul")
    fun deleteByTitle(judul: String)
}