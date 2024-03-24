package com.example.skytracker.screens

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skytracker.GiftImage
import com.example.skytracker.R
import com.example.skytracker.navigation.Screen
import com.example.skytracker.skytracker.GetDataViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable//Creator - SouravJaiswal
fun SplashScreen(
    navController: NavController,
    getDataViewModel: GetDataViewModel = hiltViewModel()
) {
    val availableData = getDataViewModel.getCity.collectAsState(initial = emptyList())
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Black.toArgb()
        }//Creator - SouravJaiswal
    }
    LaunchedEffect(key1 = Unit) {
        delay(2500L)
        navController.popBackStack()
        if (availableData.value.size > 0) {
            navController.navigate(Screen.Sky.route)
        } else {
            navController.navigate(Screen.Search.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        GiftImage(
            data = R.drawable.skytrackerholi,
            contentAlignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()//Creator - SouravJaiswal
        )
    }
}