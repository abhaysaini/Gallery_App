package com.example.galleryapp.ui.screens.images.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImagesRepository) : ViewModel() {

    private val _imagesLiveData = MutableStateFlow<PagingData<Uri>?>(null)
    val imagesLiveData: Flow<PagingData<Uri>?> = _imagesLiveData


    fun fetchImages(albumData: AlbumData) {
        viewModelScope.launch {
            val pager = repository.getImages(albumData)
            pager.cachedIn(viewModelScope).collectLatest { pagingData ->
                _imagesLiveData.value = pagingData
            }
        }
    }

    fun fetchImage(albumData: AlbumData): Flow<PagingData<Uri>> =
        repository.getImage(albumData).cachedIn(viewModelScope).catch { e ->
            Log.e("AbhaySaini", "Error fetching Album: ${e.message}", e)
        }
}