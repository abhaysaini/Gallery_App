package com.example.galleryapp.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.databinding.ItemAlbumsBinding
import com.example.galleryapp.data.models.AlbumData

class AlbumAdapter : PagingDataAdapter<AlbumData, AlbumAdapter.AlbumViewHolder>(ALBUM_COMPARATOR) {

    lateinit var binding: ItemAlbumsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        binding = ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        album?.let {
            holder.bind(it)
        }
    }

    inner class AlbumViewHolder(val binding: ItemAlbumsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: AlbumData) {
            binding.albumName.text = album.albumName
            Glide.with(itemView.context)
                .load(album.imageUri)
                .into(binding.albumImage)
            binding.albumImageCount.text = album.imageCount.toString()
            itemView.setOnClickListener {
                // Handle item click
            }

        }
    }

    companion object {
        private val ALBUM_COMPARATOR = object : DiffUtil.ItemCallback<AlbumData>() {
            override fun areItemsTheSame(oldItem: AlbumData, newItem: AlbumData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AlbumData, newItem: AlbumData): Boolean {
                return oldItem.albumName == newItem.albumName && oldItem.imageUri == newItem.imageUri
            }
        }
    }
}
