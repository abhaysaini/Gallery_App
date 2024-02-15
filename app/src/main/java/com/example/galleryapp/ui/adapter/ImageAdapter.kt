package com.example.galleryapp.ui.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.models.ImageData
import com.example.galleryapp.databinding.ItemImageBinding

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
                //
            }
        }
    }

    companion object {
        private val IMAGES_COMPARATOR = object : DiffUtil.ItemCallback<ImageData>() {
            override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem.imageUri == newItem.imageUri
            }
        }
    }
}