package com.example.galleryapp.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.galleryapp.R
import com.example.galleryapp.ui.albums.AlbumsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val splashTime: Long = 2000
    private lateinit var galleryPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startSplashTimer()
    }

    private fun startSplashTimer() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashTime)
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, AlbumsActivity::class.java)
        startActivity(intent)
        askPermission()
        finish()
    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i("abhay", "Permission Asked")
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), RESULT_CODE_GALLERY
                )
                requestGalleryPermission()
                Log.i("abhay", "Permission granted")
            }
        }
    }

    private fun requestGalleryPermission() {
        val permissionRequest: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED) {
                permissionRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }



    companion object {
        private const val RESULT_CODE_GALLERY = 1001
    }
}



//private val callables: MutableList<Callable<Void?>> = ArrayList()
//
//fun askPermissionStorage(callable: Callable<Void?>) {
//    callables.clear()
//    callables.add(callable)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.i("abhay", "Permission Asked")
//            ActivityCompat.requestPermissions(
//                this, arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                ), SplashScreenActivity.RESULT_CODE_GALLERY
//            )
//            Log.i("abhay", "Permission granted")
//        } else {
//            try {
//                callable.call()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    } else {
//        try {
//            callable.call()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}