package com.example.galleryapp.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.data.models.ImageData
import com.example.galleryapp.databinding.ItemImageBinding
import com.example.galleryapp.ui.fullScreen.ImageDetailScreenActivity

class ImageAdapter :
    PagingDataAdapter<ImageData, ImageAdapter.ImagesViewHolder>(IMAGES_COMPARATOR) {

    lateinit var binding: ItemImageBinding

    override fun onBindViewHolder(holder: ImageAdapter.ImagesViewHolder, position: Int) {
        val album = getItem(position)
        album?.let {
            holder.bind(it.imageUri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    inner class ImagesViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            Glide.with(itemView.context)
                .load(uri)
                .into(binding.imageView)
            itemView.setOnClickListener {
                val imageIntent = Intent(itemView.context, ImageDetailScreenActivity::class.java)
                imageIntent.putExtra("imageUri", uri)
                itemView.context.startActivity(imageIntent)
            }
        }
    }

    companion object {
        private val IMAGES_COMPARATOR = object : DiffUtil.ItemCallback<ImageData>() {
            override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem.imageUri == newItem.imageUri
            }
        }
    }
}