package com.example.galleryapp.ui.screens.fullScreen

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.galleryapp.databinding.ActivityImageDetailScreenBinding

class ImageDetailScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Log.i(TAG,imageUri.toString())
        Glide.with(this)
            .load(imageUri)
            .into(binding.imageDetails)
    }

    companion object{
        const val TAG = "ImageDetailScreen"
    }
}