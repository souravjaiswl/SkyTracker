package com.example.skytracker.skytracker


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material.icons.sharp.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.skytracker.GiftImage
import com.example.skytracker.R
import com.example.skytracker.model.BaseModel
import com.example.skytracker.ui.theme.GradientBackgroundBrush
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ApiWeatherIcons(val daysVal: Int) {
    Sunny(R.drawable.weatheremoji_03),
    Mostly_Sunny(R.drawable.weatheremoji_03),
    Partly_Sunny(R.drawable.weatheremoji_03),
    Intermittent_Clouds(R.drawable.weatheremoji_07),
    Hazy_Sunshine(R.drawable.weatheremoji_07),
    Mostly_Cloudy(R.drawable.weatheremoji_07),
    Cloudy(R.drawable.weatheremoji_07),
    Dreary_Overcast(R.drawable.weatheremoji_02),
    Fog(R.drawable.weatheremoji_16),
    Mostly_Cloudy_w_Showers(R.drawable.weatheremoji_08),
    Partly_Sunny_w_Showers(R.drawable.weatheremoji_08),
    T_Storms(R.drawable.weatheremoji_15),//Creator - SouravJaiswal
    Mostly_Cloudy_w_T_Storms(R.drawable.weatheremoji_09),
    Partly_Sunny_w_T_Storms(R.drawable.weatheremoji_09),
    Rain(R.drawable.weatheremoji_05),
    Flurries(R.drawable.weatheremoji_18),
    Mostly_Cloudy_w_Flurries(R.drawable.weatheremoji_07),
    Partly_Sunny_w_Flurries(R.drawable.weatheremoji_07),
    Snow(R.drawable.weatheremoji_12),
    Mostly_Cloudy_w_Snow(R.drawable.weatheremoji_10),
    Ice(R.drawable.weatheremoji_12),
    Freezing_Rain(R.drawable.weatheremoji_15),
    Rain_and_Snow(R.drawable.weatheremoji_13),
    Cold(R.drawable.weatheremoji_17),
    Clear(R.drawable.weatheremoji_19),
    Mostly_Clear(R.drawable.weatheremoji_19),
    Partly_Cloudy(R.drawable.weatheremoji_19),
    Hazy_Moonlight(R.drawable.weatheremoji_11);

    companion object {
        fun fromApiData(apiData: String): ApiWeatherIcons? {
            val cleanApiData = apiData.replace(Regex("\\s*\\(.*?\\)\\s*"), "").trim()
            return values().find {
                it.name.equals(cleanApiData.replace(" ", "_"), ignoreCase = true)
            }
        }
    }
}
@Composable
fun details(
    navController: NavController, detailsViewModel: detailsViewModel = hiltViewModel()
) {
    val currentForecasts by detailsViewModel.currentForecast.collectAsState()
    val dailyForecasts by detailsViewModel.dailyForecast.collectAsState()
    val skyBack = listOf(
        Color(0xFF1B2445), Color(0xFF9143A8)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = GradientBackgroundBrush(
                    isVerticalGradient = true,
                    colors = listOf(Color(0xFF3670AF), Color(0xFF1A3873))
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Navigate Back Button !",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(3f)// 1 + 3 + 6
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = GradientBackgroundBrush(
                            isVerticalGradient = true, colors = skyBack
                        ), shape = RoundedCornerShape(10.dp), alpha = 0.8f
                    ),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
            ) {
                AnimatedVisibility(
                    visible = currentForecasts is BaseModel.Success && dailyForecasts is BaseModel.Success,
                ) {
                    val currentData = currentForecasts as BaseModel.Success
                    val dailyData = dailyForecasts as BaseModel.Success
                    val temp = currentData.data
                    val dailytemp = dailyData.data.dailyForecasts
                    // Get the corresponding enum constant
                    val matchedIcon =
                        ApiWeatherIcons.fromApiData(if (temp.first().isDayTime) dailytemp[1].day.iconPhrase else dailytemp[1].night.iconPhrase)
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {//Creator - SouravJaiswal
                            // If a match is found, retrieve the corresponding drawable resource
                            if (matchedIcon != null) {
                                GiftImage(data = matchedIcon.daysVal, contentAlignment = Alignment.Center)
                            } else {
                                AsyncImage(
                                    modifier = Modifier.size(180.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://developer.accuweather.com/sites/default/files/${if (temp.first().isDayTime) dailytemp[1].day.icon.fixIcon() else dailytemp[1].night.icon.fixIcon()}-s.png")
                                        .build(),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = "Day/Night Icons Card View"
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column{
                                Text(
                                    text = "Tommorow",
                                    modifier = Modifier.padding(2.dp),
                                    color = Color.White,
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                )
                                Text(
                                    text = "${if (temp.first().isDayTime) dailytemp[1].temperature.max.value else dailytemp[1].temperature.min.value}",
                                    modifier = Modifier.padding(2.dp),
                                    color = Color.White,
                                    fontSize = 60.sp,
                                    fontWeight = FontWeight.W300
                                )
                                Text(
                                    text = "${if (temp.first().isDayTime) dailytemp[1].day.iconPhrase else dailytemp[1].night.iconPhrase}",
                                    modifier = Modifier.padding(2.dp),
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Serif,
                                )
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(6f)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = dailyForecasts is BaseModel.Success,
            ) {
                val dailyData = dailyForecasts as BaseModel.Success
                val Days_5_Daily_Forecasts = dailyData.data.dailyForecasts
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    items(Days_5_Daily_Forecasts) {
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SimpleDateFormat(
                                        "EEE", Locale.getDefault()
                                    ).format(Date(it.epochDate * 1000)),
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(//Creator - SouravJaiswal
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.size(50.dp),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("https://developer.accuweather.com/sites/default/files/${it.day.icon.fixIcon()}-s.png")
                                            .build(),
                                        contentScale = ContentScale.Fit,
                                        contentDescription = null
                                    )
                                    Text(
                                        text = it.day.iconPhrase,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontStyle = FontStyle.Normal,
                                        fontFamily = FontFamily.Serif,
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Sharp.ArrowDownward,
                                    contentDescription = "ArrowDownward minimum temperature",
                                    tint = Color.Red
                                )
                                Text(
                                    text = "${it.temperature.min.value}º",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Serif,
                                )
                                Icon(
                                    imageVector = Icons.Sharp.ArrowUpward,
                                    contentDescription = "ArrowUpward maximum temperature",
                                    tint = Color.Green
                                )
                                Text(
                                    text = "${it.temperature.max.value}º",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Serif,
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.size(50.dp),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("https://developer.accuweather.com/sites/default/files/${it.night.icon.fixIcon()}-s.png")
                                            .build(),
                                        contentScale = ContentScale.Fit,//Creator - SouravJaiswal
                                        contentDescription = null
                                    )
                                    Text(
                                        text = it.night.iconPhrase,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontStyle = FontStyle.Normal,
                                        fontFamily = FontFamily.Serif,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = currentForecasts is BaseModel.Loading
            ) {
                Loading()
            }
        }
    }
}//Creator - SouravJaiswal