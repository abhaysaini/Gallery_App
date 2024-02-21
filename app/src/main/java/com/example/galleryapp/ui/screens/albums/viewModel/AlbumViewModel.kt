package com.example.galleryapp.ui.screens.albums.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val _albumList = MutableStateFlow<PagingData<AlbumData>?>(null)
    val albumList: Flow<PagingData<AlbumData>?> = _albumList

    init {
        fetchAlbums()
    }

    fun fetchAlbums() {
        viewModelScope.launch {
            val pager = repository.getAlbums()
            pager.cachedIn(viewModelScope).collectLatest { pagingData ->
                _albumList.value = pagingData
            }
        }
    }
}
