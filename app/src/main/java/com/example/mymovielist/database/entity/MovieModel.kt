package com.example.mymovielist.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MovieModel(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "desc") var desc: String?,
    @ColumnInfo(name = "director") var director: String?,
    @ColumnInfo(name = "genre") var genre: String?,
    @ColumnInfo(name = "judul") var judul: String?,
    @ColumnInfo(name = "poster") var poster: String?,
    @ColumnInfo(name = "rating") var rating: String?,
    @ColumnInfo(name = "tahunRilis") var tahunRilis: String?
    ): Parcelable