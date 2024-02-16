package com.example.galleryapp.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumData (
    val id: String,
    val imageUri : Uri,
    val albumName: String,
    val imageCount:Int,
    val imagesUriData : List<Uri>
) : Parcelable