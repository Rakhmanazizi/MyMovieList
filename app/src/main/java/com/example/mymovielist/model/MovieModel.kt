package com.example.mymovielist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val desc : String? = null,
    val director : String? = null,
    val genre : String? = null,
    val judul : String? = null,
    val poster : String? = null,
    val rating : Double? = null,
    val tahunRilis : Int? = null
) : Parcelable
