package com.im.moviecatalogue.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "genreentities")
@Parcelize
data class GenreEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    var selected: Boolean = false
) : Parcelable