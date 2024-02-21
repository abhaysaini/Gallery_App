package com.example.galleryapp.data.paging

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.data.models.AlbumData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class ImagePagingSource(private val albumData: AlbumData, private val contentResolver: ContentResolver) :
    PagingSource<Int, Uri>() {

    override fun getRefreshKey(state: PagingState<Int, Uri>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Uri> {
        try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val pageSize = params.loadSize
            val offset = (page - 1) * pageSize
            val images = getImages(albumData.id, offset, pageSize)
            val prevKey = if (page > STARTING_PAGE_INDEX) page - 1 else null
            val nextKey = if (images.size == pageSize) page + 1 else null
            Log.i(TAG, "Loaded page: $page, offset: $offset, pageSize: $pageSize, images: ${images.size}")
            return LoadResult.Page(data = images, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching images", e)
            return LoadResult.Error(e)
        }
    }

    private suspend fun getImages(bucketId: String, offset: Int, limit: Int): List<Uri> {
        Log.i(TAG,"Get Images Function Called")
        val imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val selection = "${MediaStore.Images.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(bucketId)
        val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        val imagesList = mutableListOf<Uri>()

        withContext(Dispatchers.IO) {
            val cursor: Cursor? = contentResolver.query(
                imagesUri,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            cursor?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                cursor.move(offset)
                var count = 0
                while (cursor.moveToNext() && count < limit) {
                    val imageId = cursor.getLong(idColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId
                    )
                    imagesList.add(contentUri)
                    count++
                }
            }
            cursor?.close()
        }
        return imagesList
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val TAG = "ImagePagingSource"
    }
}
