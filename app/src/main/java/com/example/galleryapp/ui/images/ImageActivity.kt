package com.example.galleryapp.ui.images

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.repository.ImagesRepository
import com.example.galleryapp.databinding.ActivityImageBinding
import com.example.galleryapp.ui.adapter.ImageAdapter
import com.example.galleryapp.ui.images.viewModel.ImageViewModel
import com.example.galleryapp.ui.images.viewModel.ImageViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private val rvAdapter = ImageAdapter()
    private lateinit var viewModel: ImageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val album = intent.getParcelableExtra<AlbumData>("album")
        Log.i(TAG,album.toString())

        album?.let { viewModelInitialize(it) }
        album?.let { setUpUI(it) }
        setupRecyclerView()
        viewModel.fetchImages()
        lifecycleScope.launch {
            viewModel.imagesLiveData.collectLatest { pagingData ->
                pagingData?.let {
                    try {
                        rvAdapter.submitData(it)
                    } catch (e: Exception) {
                        Log.i(TAG,"$e  Loading Failed")
                    }
                }
            }
        }
    }

    private fun setUpUI(album: AlbumData) {
        binding.apply {
            albumName.text = album.albumName
            totalImageCount.text =
                String.format(resources.getString(R.string.image_count_format), album.imageCount)
            backButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun viewModelInitialize(album:AlbumData) {
        val factory = ImageViewModelFactory(ImagesRepository(albumData = album))
        viewModel = ViewModelProvider(this, factory)[ImageViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@ImageActivity, 3
            )
            adapter = rvAdapter
        }
    }

    companion object{
        const val TAG = "ImageActivity"
    }
}