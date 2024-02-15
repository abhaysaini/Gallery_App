package com.example.galleryapp.ui.albums.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galleryapp.data.repository.AlbumRepository

class AlbumViewModelFactory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(repository) as T
        }
        throw IllegalArgumentException(ERROR)
    }

    companion object{
        const val ERROR = "Unknown ViewModel class"
    }
}
