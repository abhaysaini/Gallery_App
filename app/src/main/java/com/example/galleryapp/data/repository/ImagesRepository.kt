package com.example.galleryapp.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.paging.AlbumPagingSource
import com.example.galleryapp.data.paging.ImagePagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImagesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getImages(albumData: AlbumData): Flow<PagingData<Uri>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(albumData,context.contentResolver) }
        ).flow
    }

    fun getImage(albumData: AlbumData) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ImagePagingSource(albumData ,context.contentResolver) }
    ).flow
}