package com.helloanwar.hellomvvm

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.helloanwar.hellomvvm.data.PhotoRepository
import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem
import com.helloanwar.hellomvvm.data.source.local.PhotoLocalSource
import com.helloanwar.hellomvvm.data.source.remote.PhotoRemoteSource
import com.helloanwar.hellomvvm.ui.theme.HelloMVVMTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel(
            PhotoRepository(
                localSource = PhotoLocalSource((application as App).database),
                PhotoRemoteSource
            ),
            application
        )

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
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (photos == null || photos?.isEmpty() == true) {
                    CircularProgressIndicator()
                } else {
                    PhotoGridList(photos)
                }
            }
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun PhotoGridList(photos: PhotoResponse? = PhotoResponse.demo()) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        photos?.let {
            items(items = it) { photo ->
                PhotoGridItem(photo)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun PhotoGridItem(photo: PhotoResponseItem) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var cardBackColor by remember { mutableStateOf(Color.LightGray) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(cardBackColor, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {

            val context = LocalContext.current

            val imageLoader = ImageLoader(context)

            val request = ImageRequest.Builder(context)
                .transformations(RoundedCornersTransformation(6.dp.value))
                .data(photo.thumbnailUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .build()

            val imagePainter = rememberImagePainter(
                request = request,
                imageLoader = imageLoader
            )

            LaunchedEffect(key1 = imagePainter) {
                launch {
                    val result = (imageLoader.execute(request) as SuccessResult).drawable
                    val bitmap = (result as BitmapDrawable).bitmap
                    val vibrant = Palette.from(bitmap)
                        .generate()
                        .getDominantColor(Color.LightGray.toArgb())
                    cardBackColor = Color(vibrant).copy(alpha = 0.15f)
                }
            }

            Image(
                painter = imagePainter,
                contentDescription = "photo",
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        Text(
            text = photo.title,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val viewModel = MainViewModel(PhotoRepository(PhotoLocalSource(), PhotoRemoteSource))

    HelloMVVMTheme {
        PhotoGallery(viewModel)
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PhotoGridPreview() {
    HelloMVVMTheme {
        PhotoGridList()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoGridItemPreview() {
    HelloMVVMTheme {
        PhotoGridItem(photo = PhotoResponse.demo().first())
    }
}