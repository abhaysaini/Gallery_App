package com.example.galleryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.databinding.ItemAlbumsBinding
import com.example.galleryapp.data.models.AlbumData

class AlbumAdapter(private val listener: OnAlbumClickListener, private val albumList: MutableList<AlbumData>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private lateinit var binding: ItemAlbumsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        binding = ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        album.let {
            holder.bind(it)
        }
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: AlbumData) {
            binding.albumName.text = album.albumName
            Glide.with(itemView.context)
                .load(album.imageUri)
                .into(binding.albumImage)
            binding.albumImageCount.text = album.imageCount.toString()
            itemView.setOnClickListener {
                listener.onAlbumClick(album)
            }
        }
    }

    interface OnAlbumClickListener {
        fun onAlbumClick(album: AlbumData)
    }
}
