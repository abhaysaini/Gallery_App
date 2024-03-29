package com.example.galleryapp.data.paging

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.data.models.AlbumData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumPagingSource(private val contentResolver: ContentResolver) :
    PagingSource<Int, AlbumData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumData> {
        try {
            Log.i(TAG, "Load Method Called")
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val nextPage = currentPage.plus(1)
            val albums = fetchAlbums(contentResolver)
            return LoadResult.Page(
                data = albums,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching albums", e)
            return LoadResult.Error(Exception("Failed to fetch albums"))
        } finally {
            Log.i(TAG, "Finally block executed")
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AlbumData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    private suspend fun fetchAlbums(contentResolver: ContentResolver): List<AlbumData> {
        val albums = mutableListOf<AlbumData>()
        val albumSet = mutableSetOf<String>()
        val albumMap = mutableMapOf<String, Pair<String, Int>>()
        var imageCount = 0
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        withContext(Dispatchers.Default) {
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder
            )
            cursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val folderId =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID))
                    if (!albumSet.contains(folderId)) {
                        albumSet.add(folderId)
                    }
                    val albumName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                    imageCount = albumMap[folderId]?.second ?: 0
                    albumMap[folderId] = albumName to (imageCount + 1)
                }
            }
            cursor?.close()
        }
        albumMap.forEach { (folderId, pair) ->
            val thumbNail = getAlbumThumbnail(contentResolver, bucketId = folderId)
            albums.add(AlbumData(folderId, thumbNail, pair.first, pair.second))
        }
        return albums
    }


    private fun getAlbumThumbnail(contentResolver: ContentResolver, bucketId: String): Uri {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA
        )
        val selection = "${MediaStore.Images.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(bucketId)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val thumbnailUriString =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                return Uri.parse("file://$thumbnailUriString")
            }
        }
        return Uri.EMPTY
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val TAG = "AlbumPagingSource"
    }
}
