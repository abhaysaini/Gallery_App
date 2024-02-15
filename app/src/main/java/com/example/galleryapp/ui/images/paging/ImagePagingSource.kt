package com.example.galleryapp.ui.images.paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.models.ImageData

class ImagePagingSource(private val albumData: AlbumData) :
    PagingSource<Int, ImageData >() {

    override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData > {
        try {
            Log.i("abhay","Load Method Called")
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val nextPage = currentPage.plus(1)
            val imageData = mutableListOf<ImageData>()
            val images = albumData.imagesUriData.forEach { it ->
                imageData.add(ImageData(it))
            }
            Log.i("abhaysaini",images.toString())
            return LoadResult.Page(
                data = imageData ,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}