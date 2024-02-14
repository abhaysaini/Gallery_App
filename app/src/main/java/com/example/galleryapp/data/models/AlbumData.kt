package com.example.galleryapp.data.models

import android.net.Uri

data class AlbumData (
    val id: String,
    val imageUri : Uri,
    val albumName: String,
    val imageCount:Int
)