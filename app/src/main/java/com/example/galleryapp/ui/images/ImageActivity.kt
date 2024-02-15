package com.example.galleryapp.ui.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.data.repository.ImagesRepository
import com.example.galleryapp.databinding.ActivityImageBinding
import com.example.galleryapp.ui.adapter.ImageAdapter
import com.example.galleryapp.ui.images.viewModel.ImageViewModel
import com.example.galleryapp.ui.images.viewModel.ImageViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageBinding
    private val rvAdapter = ImageAdapter()
    lateinit var viewModel: ImageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val album = intent.getParcelableExtra<AlbumData>("album")
        Log.i("abhaysaini",album.toString())
        val factory = ImageViewModelFactory(ImagesRepository(albumData = album!!))
        viewModel = ViewModelProvider(this, factory)[ImageViewModel::class.java]

        val dispatcher = this.onBackPressedDispatcher
        dispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        binding.apply {
            albumName.text = album.albumName
            totalImageCount.text = album.imageCount.toString() + " : Images"
            backButton.setOnClickListener {
            dispatcher.onBackPressed()
            }
        }

        setupRecyclerView()
        viewModel.fetchImages()
        lifecycleScope.launch {
            viewModel.imagesLiveData.collectLatest { pagingData ->
                pagingData?.let { rvAdapter.submitData(it) }
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
}