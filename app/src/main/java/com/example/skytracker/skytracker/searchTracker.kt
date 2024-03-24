package com.example.skytracker.skytracker


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.skytracker.ui.theme.Dark100
import com.example.skytracker.ui.theme.Dark200
import androidx.navigation.NavController
import com.example.skytracker.R
import com.example.skytracker.model.BaseModel
import com.example.skytracker.navigation.Screen
import com.example.skytracker.ui.theme.Orange
import kotlinx.coroutines.delay
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun searchTracker(
    navController: NavController,
    searchTrackerViewModel: searchTrackerViewModel = hiltViewModel(),
    setDataViewModel: SetDataViewModel = hiltViewModel()
) {//Creator - SouravJaiswal
    val citys = setDataViewModel.citys.collectAsState(initial = emptyList())
    val locations by searchTrackerViewModel.location.collectAsState()
    val (city, setCity) = remember { mutableStateOf("") }
    LaunchedEffect(city) {
        delay(500)
        if (city.isNotEmpty()) {
            searchTrackerViewModel.searchLocation(city)
        }
//        println(locations)
//        println("Citys Size : " + citys.value.size)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Orange),//Creator - SouravJaiswal
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(color = Color.Black),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(horizontal = 2.dp),
                enabled = citys.value.size > 0,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Back to skyTracker !",
                )
            }
        }
        Column(//Creator - SouravJaiswal
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .padding(horizontal = 6.dp),
        ) {
            Text(
                text = "Manage cities",
                modifier = Modifier.padding(12.dp),
                color = Color.White,
                fontSize = 34.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Left,
            )
            TextField(
                value = city,
                onValueChange = { setCity(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                placeholder = { Text(text = "Enter location") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Dark100
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            setCity("")
                        },
                        enabled = city.isNotEmpty(),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.Red,
                            disabledContentColor = Dark100
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "visibility Icon",
                        )
                    }
                },
                shape = RoundedCornerShape(46.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Blue,
                    focusedContainerColor = Dark200,
                    unfocusedContainerColor = Dark200,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchTrackerViewModel.searchLocation(city)
                    }
                ),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (city.isEmpty()) {//Creator - SouravJaiswal
                LazyColumn(
                    modifier = Modifier
                        .padding(1.dp)
                ) {
                    items(citys.value) {
                        val DEL = SwipeAction(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .padding(16.dp),
                                    imageVector = Icons.Filled.DeleteForever,
                                    contentDescription = "",
                                    tint = Color.Red
                                )
                            },
                            background = Color.Transparent,
                            isUndo = true,
                            onSwipe = {
                                setDataViewModel.deleteCity(city = it)
                            },
                        )
                        SwipeableActionsBox(
                            endActions = listOf(DEL),
                            swipeThreshold = 150.dp,
                        ) {
                            // Swipeable content goes here.
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Dark200,
                                    contentColor = MaterialTheme.colorScheme.tertiary,
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .weight(3f),
                                    ) {
                                        Text(
                                            text = it.locationName,
                                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                            color = Color.White,
                                        )
                                        Text(
                                            text = it.locationCountryName,
                                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                            fontWeight = FontWeight.Thin,
                                            fontFamily = FontFamily.Serif,
                                            color = Color.White,
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Filled.DeleteSweep,
                                        contentDescription = "Deletesweep Icon",
                                        modifier = Modifier.padding(end = 16.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = locations is BaseModel.Success,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    when (val data = locations) {
                        is BaseModel.Success -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(data.data) { location ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .clip(//Creator - SouravJaiswal
                                                RoundedCornerShape(8.dp)
                                            )
                                            .background(Color.Black)
                                            .clickable {
//                                              Database--------------------------------------
                                                 val dat = citys.value.find { it.locationName == location.englishName }?.locationName
                                                 if(dat == null){
                                                    setDataViewModel.key = location.key
                                                    setDataViewModel.name = location.englishName
                                                    setDataViewModel.countryName = location.country.englishName
                                                    setDataViewModel.addCity()
                                                    if (citys.value.size > 0) navController.popBackStack() else navController.navigate(
                                                        Screen.Sky.route
                                                    )
                                                 }else {
                                                     if (citys.value.size > 0)
                                                         navController.popBackStack()
                                                     else
                                                         navController.navigate(Screen.Sky.route)
                                                 }
                                            }
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = location.englishName,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                location.country.englishName,
                                                color = Color.Gray,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                            if (data.data == emptyList<String>()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center//Creator - SouravJaiswal
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "Not Found !",
                                        modifier = Modifier
                                            .padding(4.dp),
                                        tint = Color.Red
                                    )
                                    Text(
                                        text = "City not found !",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.W100,
                                        fontFamily = FontFamily.Serif,
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
            AnimatedVisibility(
                visible = locations is BaseModel.Loading,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            AnimatedVisibility(
                visible = locations is BaseModel.Error,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (locations.toString() == "Error(error=Unable to resolve host \"dataservice.accuweather.com\": No address associated with hostname)") {
                        Image(//Creator - SouravJaiswal
                            painter = painterResource(id = R.drawable.wifi512),
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null,
                            alignment = Alignment.TopCenter
                        )
                        Spacer(modifier = Modifier.height(10.dp)) //We use spacer to space.
                        Text(
                            text = "Somthing went wrong",
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W200,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(5.dp)) //We use spacer to space.
                        Text(
                            text = "Check your connection, then refresh the page.",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp)) //We use spacer to space.
                        TextButton(
                            onClick = {
                                searchTrackerViewModel.searchLocation(city)
                            },
                            shape = RoundedCornerShape(60.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color(0xFF0091EA)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF0091EA))
                        ) {
                            Text(
                                text = "Refresh",
                                modifier = Modifier.padding(horizontal = 20.dp),
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.W400,
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Data provided in part by",
                modifier = Modifier.padding(2.dp),
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
            )
            Icon(
                painter = painterResource(id = R.drawable.accuicon),
                contentDescription = "AccuWeatherIcon",
            )
            Text(
                text = "AccuWeather",
                modifier = Modifier.padding(2.dp),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}//Creator - SouravJaiswal