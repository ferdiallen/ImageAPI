package com.example.imagesapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.imagesapi.navigation.NavigationCommander
import com.example.imagesapi.navigation.NavigationQueue
import com.example.imagesapi.ui.theme.ImagesAPITheme
import com.example.imagesapi.viewmodels.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagesAPITheme {
                NavigationCommander()
            }
        }
    }
}

@Composable
fun SplashScreens(nav: NavHostController) {
    val result by
    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.catanimation))
    val progress = animateLottieCompositionAsState(
        composition = result,
        iterations = 1,
        restartOnPlay = false,
        isPlaying = true,
        speed = 2.5F
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = result,
                progress = progress.value,
                modifier = Modifier.size(320.dp), contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(0.9F),
                color = Color.Green,
                progress = progress.value
            )
        }
    }
    if (progress.progress == 1F) {
        LaunchedEffect(key1 = Unit) {
            nav.navigate(NavigationQueue.MainScreen.route)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalCoilApi
@Composable
fun MainDisplay(data: ViewModelMain = hiltViewModel()) {
    var showSearchBar by remember {
        mutableStateOf(false)
    }
    val respondData by data.myData
    val texts by data.searchText
    Log.d("TAG", texts)
    Column(Modifier.fillMaxSize()) {

        AnimatedVisibility(visible = showSearchBar) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            ) {
                TextField(
                    value = data.searchText.value,
                    onValueChange = data::searchText,
                    singleLine = true,
                    maxLines = 1, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F), keyboardActions = KeyboardActions(onDone = {

                    }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                IconButton(onClick = { showSearchBar = false }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Return")
                }
            }
        }
        AnimatedVisibility(visible = !showSearchBar) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.mainTitle),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(3F)
                )
                Spacer(Modifier.weight(1F))
                IconButton(onClick = { data.getRandomData() }, modifier = Modifier.weight(1F)) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh button"
                    )
                }
                IconButton(onClick = { showSearchBar = true }, Modifier.weight(1F)) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search Icons"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = respondData) { out ->
                ImageTiles(data = out.url, authName = out.artist_name)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun ImageTiles(data: String, authName: String) {
    val imageGetter = rememberImagePainter(data = data)
    Card(
        modifier = Modifier
            .size(200.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White, elevation = 8.dp
    ) {
        when (imageGetter.state) {
            is ImagePainter.State.Loading -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp), strokeWidth = 3.dp
                )
            }
            else -> {
                Image(
                    painter = imageGetter,
                    contentDescription = "ImageData",
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }
        }

    }
}
