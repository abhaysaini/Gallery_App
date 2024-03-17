package com.example.galleryapp.ui.screens.images

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.repository.ImagesRepository
import com.example.galleryapp.databinding.ActivityImageBinding
import com.example.galleryapp.ui.adapter.ImageAdapter
import com.example.galleryapp.ui.screens.images.viewModel.ImageViewModel
import com.example.galleryapp.ui.screens.images.viewModel.ImageViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private val rvAdapter = ImageAdapter()
    private val viewModel: ImageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val album = intent.getParcelableExtra<AlbumData>("album")
        Log.i(TAG,album.toString())

        album?.let { setUpUI(it) }
        setupRecyclerView()
        album?.let { viewModel.fetchImages(it) }
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