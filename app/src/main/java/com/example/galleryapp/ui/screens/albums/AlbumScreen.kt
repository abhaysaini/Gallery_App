package com.example.galleryapp.ui.screens.albums

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.ui.adapter.AlbumAdapter
import com.example.galleryapp.ui.screens.images.ImageActivity
import com.example.galleryapp.ui.screens.images.ImageScreen
import com.example.galleryapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumScreen : ComponentActivity(), AlbumAdapter.OnAlbumClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val album = remember {
                mutableStateOf<List<AlbumData>>(emptyList())
            }
            LaunchedEffect(key1 = true, block = {
                album.value = AppUtils().fetchAlbums(contentResolver)
            })

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Black)
            ) {
                Text(
                    text = "Essential Albums",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC58BF2),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    items(album.value) {
                        val painter = rememberAsyncImagePainter(model = it.imageUri)
                        Column {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp)
                                    .background(Color.Black)
                                    .clickable {
                                        onAlbumClick(it)
                                    }
                                ,
                                border = BorderStroke(1.dp,Color(0xFF727272)),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Column {
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .fillMaxHeight()
                                            .background(Color.Black)
                                    )
                                }
                            }
                            Text(
                                text = it.albumName,
                                color = Color(0xFFC58BF2),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Default,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                ,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis

                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${it.imageCount}",
                                color = Color(0xFFC58BF2),
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Default,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                ,
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onAlbumClick(album: AlbumData) {
        val intent = Intent(this, ImageScreen::class.java)
        intent.putExtra("album", album)
        startActivity(intent)
    }
}