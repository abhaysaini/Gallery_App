package com.example.galleryapp.ui.fullScreen

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.example.galleryapp.databinding.ActivityImageDetailScreenBinding

class ImageDetailScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dispatcher = this.onBackPressedDispatcher
        dispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        binding.backButton.setOnClickListener {
            dispatcher.onBackPressed()
        }

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Log.i("saini",imageUri.toString())
        Glide.with(this)
            .load(imageUri)
            .into(binding.imageDetails)
    }
}