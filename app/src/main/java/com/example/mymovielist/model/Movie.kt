package com.example.mymovielist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id : String? = null,
    val desc : String? = null,
    val director : String? = null,
    val genre : String? = null,
    var judul : String? = null,
    val poster : String? = null,
    val rating : Double? = null,
    val tahunRilis : Int? = null
) : Parcelable
