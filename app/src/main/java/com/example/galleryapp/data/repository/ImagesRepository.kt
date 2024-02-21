package com.example.galleryapp.data.repository

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImagesRepository(private val albumData: AlbumData,private val contentResolver: ContentResolver) {
    fun getImages(): Flow<PagingData<Uri>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(albumData,contentResolver) }
        ).flow
    }
}