package com.onstonboy.csgowallpaper.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallpaper(
    var url: String,
    var name: String
) : Parcelable
