package com.example.galleryapp.ui.images.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.models.ImageData
import com.example.galleryapp.data.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ImageViewModel (private val repository: ImagesRepository): ViewModel() {

    private val _imagesLiveData = MutableStateFlow<PagingData<ImageData>?>(null)
    val imagesLiveData: Flow<PagingData<ImageData>?> = _imagesLiveData

    init {
        fetchImages()
    }

    fun fetchImages() {
        viewModelScope.launch {
            val pager = repository.getImages()
            pager.cachedIn(viewModelScope).collectLatest { pagingData ->
                _imagesLiveData.value = pagingData
            }
        }
    }
}