package com.example.galleryapp.ui.fullScreen

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.galleryapp.R

class ImageDetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        setContent {
            val backDispatcher = this.onBackPressedDispatcher
            backDispatcher.addCallback(object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    finish()
                }

            })
            ImageDetailScreen(imageUri,backDispatcher)
        }
    }
}

@Composable
fun ImageDetailScreen(imageUri: Uri?,backPressedDispatcher: OnBackPressedDispatcher) {
    Surface(color = MaterialTheme.colorScheme.background) {
        var scale by remember {
            mutableFloatStateOf(1f)
        }
        val painter =
            rememberImagePainter(data = imageUri)
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MyToolbar {
                    backPressedDispatcher.onBackPressed()
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, gestureZoom, _ ->
                            scale = (scale * gestureZoom).coerceIn(0.8f, 3f)
                        }
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .weight(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_button),
                    contentDescription = "Back Button",
                    tint = Color(0xFFC58BF2)
                )
            }
        }
    )
}
