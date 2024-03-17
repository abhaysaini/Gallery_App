package com.example.galleryapp.data.repository

import android.content.ContentResolver
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.paging.AlbumPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AlbumRepository @Inject constructor(@ApplicationContext private val context: Context){
    fun getAlbums(): Flow<PagingData<AlbumData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumPagingSource(context.contentResolver) }
        ).flow
    }

    fun getAlbum() = Pager(
        config = PagingConfig(
            pageSize = 1,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { AlbumPagingSource(context.contentResolver) }
    ).flow
}
