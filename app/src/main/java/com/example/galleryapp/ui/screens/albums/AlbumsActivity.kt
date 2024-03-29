package com.example.galleryapp.ui.screens.albums

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.data.repository.AlbumRepository
import com.example.galleryapp.ui.adapter.AlbumAdapter
import com.example.galleryapp.ui.screens.albums.viewModel.AlbumViewModel
import com.example.galleryapp.ui.screens.albums.viewModel.AlbumViewModelFactory
import com.example.galleryapp.ui.screens.images.ImageActivity
import com.example.galleryapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AlbumsActivity : AppCompatActivity(), AlbumAdapter.OnAlbumClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AlbumViewModel by viewModels()
    private var albumsList = mutableListOf<AlbumData>()
    private lateinit var albumAdapter : AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
           albumsList = AppUtils().fetchAlbums(contentResolver)
            setupRecyclerView(albumsList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun setupRecyclerView(album: MutableList<AlbumData>) {
        withContext(Dispatchers.Main) {
            albumAdapter = AlbumAdapter(this@AlbumsActivity,album)
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(
                    this@AlbumsActivity, 3
                )
                adapter = albumAdapter
            }
            albumAdapter.notifyDataSetChanged()
        }
    }

    override fun onAlbumClick(album: AlbumData) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("album", album)
        startActivity(intent)
    }
}