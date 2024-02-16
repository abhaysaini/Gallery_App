package com.example.galleryapp.ui.splash


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galleryapp.R
import com.example.galleryapp.ui.albums.AlbumsActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenContent()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun SplashScreenContent() {
        val context = LocalContext.current
        val permission =
            rememberPermissionState(permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE)
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000)
                    navigateToMainActivity(context)
                }
            } else {
                appSettingOpen(this)
            }
        }
        LaunchedEffect(key1 = permission) {
            if (permission.hasPermission) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    navigateToMainActivity(this@SplashScreen)
                }
            } else {
                askPermission(permissionLauncher)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_img),
                contentDescription = null,
                modifier = Modifier
                    .size(310.dp)
                    .fillMaxHeight()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Gallery App",
                color = Color(0xFF98BFFD),
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = (-100).dp)
            )
            Text(
                text = "Explore your visual story",
                color = Color(0xFF98BFFD),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = -90.dp)
            )
        }
    }

    private fun navigateToMainActivity(context: Context) {
        val intent = Intent(context, AlbumsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun appSettingOpen(context: Context) {
        Toast.makeText(
            context,
            "Go to Setting and Enable All Permission",
            Toast.LENGTH_LONG
        ).show()

        val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingIntent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(settingIntent)
    }

    private fun askPermission(
        permissionLauncher: ActivityResultLauncher<String>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    SplashScreenContent()
}
