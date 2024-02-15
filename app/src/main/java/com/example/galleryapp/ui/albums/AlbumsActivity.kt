package com.example.galleryapp.ui.albums

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.data.repository.AlbumRepository
import com.example.galleryapp.ui.adapter.AlbumAdapter
import com.example.galleryapp.ui.albums.viewModel.AlbumViewModel
import com.example.galleryapp.ui.albums.viewModel.AlbumViewModelFactory
import com.example.galleryapp.ui.images.ImageActivity
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlbumsActivity : AppCompatActivity(), AlbumAdapter.OnAlbumClickListener {

    lateinit var binding: ActivityMainBinding
    private val rvAdapter = AlbumAdapter(this)
    lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelInitialize()
        setupRecyclerView()
        viewModel.fetchAlbums()
        lifecycleScope.launch {
            viewModel.albumList.collectLatest { pagingData ->
                pagingData?.let { rvAdapter.submitData(it) }
            }
        }
    }

    private fun viewModelInitialize() {
        val factory = AlbumViewModelFactory(AlbumRepository(contentResolver))
        viewModel = ViewModelProvider(this, factory)[AlbumViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@AlbumsActivity, 3
            )
            adapter = rvAdapter
        }
    }

    override fun onAlbumClick(album: AlbumData) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("album", album)
        startActivity(intent)
    }
}