package com.example.galleryapp.ui.albums

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        if(!checkPermission()){
           appSettingOpen(this)
        }
        else{
            setupRecyclerView()
            viewModel.fetchAlbums()
            lifecycleScope.launch {
                viewModel.albumList.collectLatest { pagingData ->
                    pagingData?.let { rvAdapter.submitData(it) }
                }
            }
        }
    }

    private fun checkPermission():Boolean{
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_DENIED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        ){
            return false
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED){
            return false
        }
        else if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ){
            return false
        }
        return true
    }

    private fun appSettingOpen(context: Context){
        Toast.makeText(
            context,
            "Go to Setting and Enable All Permission",
            Toast.LENGTH_LONG
        ).show()

        val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingIntent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(settingIntent)
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