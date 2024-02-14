package com.example.galleryapp.data.repository

import android.content.ContentResolver
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.ui.albums.paging.AlbumPagingSource
import kotlinx.coroutines.flow.Flow

class AlbumRepository (private val contentResolver: ContentResolver){

    fun getAlbums(): Flow<PagingData<AlbumData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumPagingSource(contentResolver) }
        ).flow
    }
}
