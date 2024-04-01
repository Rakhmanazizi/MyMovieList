package com.example.mymovielist.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movieApp")
@Parcelize
data class MovieModelRoom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id : Int = 0,

    @ColumnInfo(name = "judul") var judul : String = "",

    @ColumnInfo(name = "genre") var genre : String = "",

    @ColumnInfo(name = "rating") var rating : Double = 0.0,

    @ColumnInfo(name = "tahunRilis") var tahunRilis : Int = 0,

    @ColumnInfo(name = "poster") var poster : String = ""
) : Parcelable
