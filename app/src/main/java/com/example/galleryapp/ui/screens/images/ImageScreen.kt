package com.example.galleryapp.ui.screens.images

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.galleryapp.R
import com.example.galleryapp.data.models.AlbumData
import com.example.galleryapp.ui.screens.images.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.layout.ContentScale
import androidx.paging.compose.itemContentType
import coil.compose.rememberAsyncImagePainter
import com.example.galleryapp.ui.screens.fullScreen.ImageDetailScreen


@AndroidEntryPoint
class ImageScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val album = intent.getParcelableExtra<AlbumData>("album")
        setContent {
            val viewModel = hiltViewModel<ImageViewModel>()
            val images = album?.let { viewModel.fetchImage(it).collectAsLazyPagingItems() }
            val backDispatcher = this.onBackPressedDispatcher
            backDispatcher.addCallback(object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
            Column(modifier = Modifier.background(Color.Black)) {
                if (album != null) {
                    MyToolbar(albumData = album, backDispatcher)
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    if (images != null) {
                        items(
                            count = images.itemCount,
                            contentType = images.itemContentType { "contentType" }) { index ->
                            val image = images[index]
                            val painter = rememberAsyncImagePainter(model = image)

                            Image(
                                painter = painter,
                                contentDescription = "Album Image",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .defaultMinSize(minWidth = 160.dp, minHeight = 160.dp)
                                    .clickable {
                                        val intent =
                                            Intent(this@ImageScreen, ImageDetailScreen::class.java)
                                        intent.putExtra("imageUri", image)
                                        startActivity(intent)
                                    },
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyToolbar(albumData: AlbumData, backPressedDispatcher: OnBackPressedDispatcher) {
    Row(modifier = Modifier.background(Color.Black)) {
        IconButton(
            onClick = { backPressedDispatcher.onBackPressed() },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_button),
                contentDescription = "Back Button",
                tint = Color(0xFFC58BF2)
            )
        }

        Column {
            Text(
                text = albumData.albumName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                color = Color(0xFFC58BF2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Text(
                text = "${albumData.imageCount} : Images",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                color = Color(0xFFC58BF2),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }

    }
}