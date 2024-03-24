package com.example.skytracker.skytracker

import android.app.Activity
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.skytracker.R
import com.example.skytracker.api.APIKEY
import com.example.skytracker.model.BaseModel
import com.example.skytracker.navigation.Screen
import com.example.skytracker.ui.theme.GradientBackgroundBrush
import com.example.skytracker.ui.theme.rk200
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalPagerApi::class)
@Composable
fun skyTracker(
    navController: NavController,//Creator - SouravJaiswal
    skyTrackerViewModel: skyTrackerViewModel = hiltViewModel(),
    getDataViewModel: GetDataViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val currentForecasts by skyTrackerViewModel.currentForecast.collectAsState()
    val dailyForecasts by skyTrackerViewModel.dailyForecast.collectAsState()
    val hourlyForecasts by skyTrackerViewModel.hourlyForecast.collectAsState()
    val getCity = getDataViewModel.getCity.collectAsState(initial = emptyList())
    val pagerState = rememberPagerState()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color(0xFF1B2445).toArgb()
        }
    }
    val skyBack = listOf(
        Color(0xFF1B2445),
        Color(0xFF9143A8)
    )
    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(1000)//Creator - SouravJaiswal
        skyTrackerViewModel.getCurrentForecast(getCity.value[pagerState.currentPage].locationKey)
        skyTrackerViewModel.getHourlyForecast(getCity.value[pagerState.currentPage].locationKey)
        skyTrackerViewModel.getDailyForecast(getCity.value[pagerState.currentPage].locationKey)
    }
    HorizontalPager(
        modifier = Modifier
            .fillMaxSize(),
        count = getCity.value.size,
        state = pagerState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = GradientBackgroundBrush(
                        isVerticalGradient = true,
                        colors = skyBack
                    )
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Search.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Sharp.Add,
                        contentDescription = "Add City !",
                        tint = Color.White
                    )
                }
                AnimatedVisibility(
                    visible = hourlyForecasts is BaseModel.Success
                ) {
                    Text(
                        text = getCity.value[currentPage].locationName,
                        color = Color.White,
                        fontSize = 26.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W300,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            delay(1500)
                            skyTrackerViewModel.getCurrentForecast(getCity.value[pagerState.currentPage].locationKey)
                            skyTrackerViewModel.getHourlyForecast(getCity.value[pagerState.currentPage].locationKey)
                            skyTrackerViewModel.getDailyForecast(getCity.value[pagerState.currentPage].locationKey)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Repeat,
                        contentDescription = "Disable Icon", //This Icon is Disabled
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)// 4 + 3 + 2
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = currentForecasts is BaseModel.Success && dailyForecasts is BaseModel.Success
                ) {
//                    val currentData = currentForecasts as BaseModel.Success
                    when (val currentData = currentForecasts) {
                        is BaseModel.Success -> {
                            val dailyData = dailyForecasts as BaseModel.Success
                            val temp = currentData.data
                            val dailytemp = dailyData.data.dailyForecasts
//                            AsyncImage(
//                                modifier = Modifier.size(70.dp),
//                                model = ImageRequest.Builder(LocalContext.current)
////                            .data("https://developer.accuweather.com/sites/default/files/${temp.first().weatherIcon.fixIcon()}-s.png")
//                                    .data(R.drawable.weatheremoji_19)
//                                    .build(),
//                                contentDescription = null
//                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                val context = LocalContext.current
                                val imageLoader = ImageLoader.Builder(context)
                                    .components {
                                        if (Build.VERSION.SDK_INT >= 28) {
                                            add(ImageDecoderDecoder.Factory())
                                        } else {
                                            add(GifDecoder.Factory())
                                        }
                                    }
                                    .build()
                                HorizontalPagerIndicator(
                                    pagerState = pagerState,
                                    modifier = Modifier.padding(4.dp),
                                    activeColor = Color(0xffEC407A),
                                    inactiveColor = Color.Gray,
                                    spacing = 8.dp,
                                    indicatorShape = CircleShape
                                )
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = if (temp.first().isDayTime) R.drawable.weatheremoji_03 else R.drawable.weatheremoji_19,
                                        imageLoader = imageLoader,
                                        filterQuality = FilterQuality.High
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(140.dp),
                                )
                                Text(
                                    text = "${temp.first().temperature.metric.value}℃",
                                    fontWeight = FontWeight.W300,
                                    fontSize = 80.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Default
                                )
                                Text(
                                    text = "${temp.first().weatherText} ${dailytemp.first().temperature.min.value}º / ${dailytemp.first().temperature.max.value}º",
                                    color = Color.White,
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                )
                            }
                        }

                        else -> {}
                    }
                }
                AnimatedVisibility(
                    visible = hourlyForecasts is BaseModel.Loading
                ) {
                    Loading()
                }
            }
            Row(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = rk200,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarMonth,
                                contentDescription = "Calendar",
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(16.dp),
                                tint = Color.Gray
                            )
                            Text(
                                text = "5-day forecast",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextButton(
                                onClick = { navController.navigate(Screen.Details.getByID(getCity.value[pagerState.currentPage].locationKey)) },
                                enabled = dailyForecasts is BaseModel.Success,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color.Gray,
                                    disabledContentColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    text = "More details",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.SansSerif,
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = "Arrow",
                                modifier = Modifier
                                    .padding(horizontal = 1.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxSize(),
                    ) {
                        AnimatedVisibility(
                            visible = currentForecasts is BaseModel.Success && dailyForecasts is BaseModel.Success
                        ) {
//                            val dailyData = dailyForecasts as BaseModel.Success
                            when (val dailyData = dailyForecasts) {
                                is BaseModel.Success -> {
                                    val currentData = currentForecasts as BaseModel.Success
                                    val temp = currentData.data
                                    val dailytemp = dailyData.data.dailyForecasts
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        items(dailytemp) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(2.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxSize(),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    AsyncImage(
                                                        modifier = Modifier.size(70.dp),
                                                        model = ImageRequest.Builder(LocalContext.current)
                                                            .data("https://developer.accuweather.com/sites/default/files/${if (temp.first().isDayTime) it.day.icon.fixIcon() else it.night.icon.fixIcon()}-s.png")
                                                            .build(),
                                                        contentScale = ContentScale.Fit,
                                                        contentDescription = null
                                                    )
                                                    Column {
                                                        Text(
                                                            text = SimpleDateFormat(
                                                                "EEE",
                                                                Locale.getDefault()
                                                            ).format(
                                                                Date(
                                                                    it.epochDate * 1000
                                                                )
                                                            ),
                                                            modifier = Modifier
                                                                .padding(horizontal = 3.dp),
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Text(
                                                            text = if (temp.first().isDayTime) it.day.iconPhrase else it.night.iconPhrase,
                                                            color = Color.White,
                                                            fontSize = 12.sp,
                                                            fontStyle = FontStyle.Normal,
                                                            fontFamily = FontFamily.Serif,
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxSize(),
                                                    horizontalArrangement = Arrangement.End,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    Text(
                                                        text = "${it.temperature.min.value}º / ${it.temperature.max.value}º",
                                                        color = Color.White,
                                                        fontFamily = FontFamily.Serif,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                else -> {}
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))//Creator - SouravJaiswal
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(2f)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Hourly Forecasts:",
                        color = Color.Yellow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(//Creator - SouravJaiswal
                        text = "Next 12hr >",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedVisibility(
                    visible = hourlyForecasts is BaseModel.Success
                ) {
                    when (val data = hourlyForecasts) {
                        is BaseModel.Success -> {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(data.data) { forecast ->
                                    Column(
                                        modifier = Modifier
                                            .size(100.dp, 140.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(rk200),
                                        verticalArrangement = Arrangement.Center,//Creator - SouravJaiswal
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = SimpleDateFormat("H a").format(Date(forecast.epochDateTime * 1000)),
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        AsyncImage(
                                            modifier = Modifier.size(70.dp),
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data("https://developer.accuweather.com/sites/default/files/${forecast.weatherIcon.fixIcon()}-s.png")
                                                .build(),
                                            contentScale = ContentScale.Fit,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = forecast.temperature.value.toString() + "°",//Creator - SouravJaiswal
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
                AnimatedVisibility(visible = hourlyForecasts is BaseModel.Loading) {
                    Loading()
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

fun Int.fixIcon(): String {
    return if (this > 9) {
        toString()
    } else {
        "0${this}"
    }
}

@Composable
fun Loading() {//Creator - SouravJaiswal
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.White)
    }
}
