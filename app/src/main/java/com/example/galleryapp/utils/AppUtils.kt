package com.example.galleryapp.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.example.galleryapp.data.models.AlbumData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class AppUtils {

    suspend fun fetchAlbums(contentResolver: ContentResolver): MutableList<AlbumData> {
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
}