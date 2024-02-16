//package com.example.galleryapp.ui.albums
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.lifecycle.ViewModelProvider
//import com.example.galleryapp.data.models.AlbumData
//import com.example.galleryapp.ui.albums.viewModel.AlbumViewModel
//import com.example.galleryapp.ui.images.ImageActivity
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.paging.PagingData
//import coil.annotation.ExperimentalCoilApi
//import coil.compose.rememberImagePainter
//import com.example.galleryapp.R
//import androidx.paging.compose.collectAsLazyPagingItems
//
//class AlbumsScreen : ComponentActivity() {
//    private lateinit var viewModel: AlbumViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        viewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
//        viewModel.fetchAlbums()
//        setContent {
//            val lazyAlbums = viewModel.albumList.collectAsLazyPagingItems()
//            val albums = lazyAlbums.value
//
//            if (albums != null) {
//                AlbumsActivityC(
//                    albums = albums,
//                    onAlbumClick = { album -> navigateToImageActivity(album) }
//                )
//            }
//        }
//    }
//
//    private fun navigateToImageActivity(album: AlbumData) {
//        val intent = Intent(this, ImageActivity::class.java)
//        intent.putExtra("album", album)
//        startActivity(intent)
//    }
//}
//
//
//@Composable
//fun AlbumsActivityC(albums: PagingData<AlbumData>, onAlbumClick: (AlbumData) -> Unit) {
//    Surface(color = MaterialTheme.colorScheme.background) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            Text(
//                text = stringResource(id = R.string.album),
//                fontSize = 20.sp,
//                color = Color(0xFFC58BF2),
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            )
//            AlbumsList(albums = albums, onAlbumClick = onAlbumClick)
//        }
//    }
//}
//
//@Composable
//fun AlbumsList(albums: PagingData<AlbumData>, onAlbumClick: (AlbumData) -> Unit) {
//    LazyColumn{
//        items(
//            albums
//        ){album->
//            AlbumItem(album = album, onAlbumClick = onAlbumClick)
//
//        }
//    }
//}
//
//@OptIn(ExperimentalCoilApi::class)
//@Composable
//fun AlbumItem(album: AlbumData, onAlbumClick: (AlbumData) -> Unit) {
//    Surface(
//        modifier = Modifier
//            .padding(4.dp)
//            .clickable { onAlbumClick(album) },
//        shape = MaterialTheme.shapes.medium,
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = rememberImagePainter(data = album.imageUri),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(shape = RoundedCornerShape(8.dp))
//            )
//            Text(
//                text = album.albumName,
//                fontSize = 16.sp,
//                color = Color(0xFFC58BF2),
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//            Text(
//                text = stringResource(id = R.string.imageCount, album.imageCount),
//                fontSize = 12.sp,
//                color = Color(0xFFC58BF2),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//        }
//    }
//}
//
