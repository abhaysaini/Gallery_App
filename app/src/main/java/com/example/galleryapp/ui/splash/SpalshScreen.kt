//package com.example.galleryapp.ui.splash
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import com.example.galleryapp.R
//import com.example.galleryapp.ui.albums.AlbumsActivity
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import androidx.compose.material3.Text
//
//class SplashScreen : ComponentActivity() {
//
//    private val splashTime: Long = 2000
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                Log.i("abhay", "Permission granted")
//            } else {
//                Log.i("abhay", "Permission denied")
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            SplashScreenContent()
//        }
//        startSplashTimer()
//    }
//
//    @Composable
//    private fun startSplashTimer() {
//        val scope = rememberCoroutineScope()
//        scope.launch {
//            delay(splashTime)
//            navigateToMainActivity()
//        }
//    }
//
//    private fun navigateToMainActivity() {
//        val intent = Intent(this, AlbumsActivity::class.java)
//        startActivity(intent)
//        askPermission()
//        finish()
//    }
//
//    private fun askPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                Log.i("abhay", "Permission Asked")
//                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//                requestGalleryPermission()
//            }
//        }
//    }
//
//    private fun requestGalleryPermission() {
//        val permissionRequest: MutableList<String> = ArrayList()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                permissionRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
//            }
//        }
//    }
//
//    @Composable
//    fun SplashScreenContent() {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.splash_img),
//                contentDescription = "Splash Image",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(bottom = 16.dp)
//            )
//            Text(
//                text = "Gallery App",
//                color = MaterialTheme.colorScheme.secondary,
//                fontSize = 38.dp,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleMedium
//            )
//            Text(
//                text = "Explore your visual story",
//                color = MaterialTheme.colorScheme.secondary,
//                fontSize = 24.dp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 16.dp),
//                style = MaterialTheme.typography.titleMedium
//            )
//        }
//    }
//}