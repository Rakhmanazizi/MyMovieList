package com.example.mymovielist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieModel: MovieModelRoom)

    @Delete
    fun delete(movieModel: MovieModelRoom)

    @Query("SELECT * FROM movieApp ORDER BY Judul ASC")
    fun getAllMember(): LiveData<List<MovieModelRoom>>
}