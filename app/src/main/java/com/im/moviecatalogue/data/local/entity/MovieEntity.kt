package com.im.moviecatalogue.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movieentities")
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var title: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("vote_average")
    var rate: String,
    @SerializedName("overview")
    var synopsis: String,
    @SerializedName("poster_path")
    var poster: String? = null
): Parcelable
