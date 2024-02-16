package com.example.galleryapp.ui.screens.images.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galleryapp.data.repository.ImagesRepository

class ImageViewModelFactory(private val repository: ImagesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(repository) as T
        }
        throw IllegalArgumentException(ERROR)
    }

    companion object{
        const val ERROR = "Unknown ViewModel class"
    }
}

