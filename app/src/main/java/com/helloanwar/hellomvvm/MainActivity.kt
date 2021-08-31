package com.helloanwar.hellomvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.helloanwar.hellomvvm.data.PhotoRepository
import com.helloanwar.hellomvvm.data.source.remote.PhotoRemoteSource
import com.helloanwar.hellomvvm.ui.theme.HelloMVVMTheme

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel(PhotoRepository(PhotoRemoteSource))

        setContent {
            HelloMVVMTheme {
                // A surface container using the 'background' color from the theme
                PhotoGallery(viewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PhotoGallery(viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "My Photo Album")
            })
        },
        content = {

            val photos by viewModel.photos.observeAsState()

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (photos == null || photos?.isEmpty() == true) {
                    CircularProgressIndicator()
                } else {
                    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                        photos?.let {
                            items(items = it) { photo ->
                                Column(Modifier.fillMaxWidth()) {
                                    Image(
                                        painter = rememberImagePainter(
                                            photo.thumbnailUrl,
                                            builder = {
                                                placeholder(R.drawable.ic_launcher_foreground)
                                                error(R.drawable.ic_launcher_foreground)
                                            }),
                                        contentDescription = "photo"
                                    )
                                    Text(text = photo.title)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val viewModel = MainViewModel(PhotoRepository(PhotoRemoteSource))

    HelloMVVMTheme {
        PhotoGallery(viewModel)
    }
}