package com.example.galleryapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.models.ImageData
import com.example.galleryapp.data.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImagesRepository(private val albumData: AlbumData) {
    fun getImages(): Flow<PagingData<ImageData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(albumData) }
        ).flow
    }
}