package com.bawp.jetweatherforecast.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.jetweatherforecast.data.DataOrException
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.model.WeatherItem
import com.bawp.jetweatherforecast.navigation.WeatherScreens
import com.bawp.jetweatherforecast.screens.main.MainViewModel
import com.bawp.jetweatherforecast.screens.settings.SettingsViewModel
import com.bawp.jetweatherforecast.utils.formatDate
import com.bawp.jetweatherforecast.utils.formatDecimals
import com.bawp.jetweatherforecast.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {

    val curCity: String = if(city!!.isBlank()) "Seattle"
                          else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("metric")
    }
    var isMetric by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isMetric = unit == "metric"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(
                city = curCity,
                units = unit
            )
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController = navController, isMetric = isMetric)
        }
    }
}

@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
    isMetric: Boolean,
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", " + weather.city.country,
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp
            ) {
                Log.d("TAG", "MainScaffold: Button Clicked")
            }
        }
    ) {
        MainContent(data = weather, isMetric = isMetric)
    }
}

@Composable
fun MainContent(data: Weather, isMetric: Boolean) {

    val weatherItem = data.list[0]
    val imagesUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imagesUrl)
                //image
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0], isMetric = isMetric)
        Divider()
        SunsetSunRiseRow(weather = data.list[0])
        Text(
            text = "This Week",
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}


