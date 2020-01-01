package com.im.moviecatalogue.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(tableName = "favoriteentities")
@Parcelize
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var title: String,
    var date: String,
    var rate: String,
    var synopsis: String,
    var poster: String? = null,
    var category: String

): Parcelable