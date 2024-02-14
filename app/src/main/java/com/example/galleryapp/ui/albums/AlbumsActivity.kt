package com.example.galleryapp.ui.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.data.repository.AlbumRepository
import com.example.galleryapp.ui.adapter.AlbumAdapter
import com.example.galleryapp.ui.albums.viewModel.AlbumViewModel
import com.example.galleryapp.ui.albums.viewModel.AlbumViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlbumsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val rvAdapter = AlbumAdapter()
    lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = AlbumViewModelFactory(AlbumRepository(contentResolver))
        viewModel = ViewModelProvider(this, factory)[AlbumViewModel::class.java]
        setupRecyclerView()
        viewModel.fetchAlbums()

        lifecycleScope.launch {
            viewModel.albumList.collectLatest { pagingData ->
                pagingData?.let { rvAdapter.submitData(it) }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@AlbumsActivity, 3
            )
            adapter = rvAdapter
        }
    }
}