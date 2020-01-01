package com.im.moviecatalogue.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Trailer(
    var key: String
): Parcelable
