package com.example.imagesapi.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.imagesapi.MainDisplay
import com.example.imagesapi.SplashScreens


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun NavigationCommander() {
    val navHost = rememberNavController()
    NavHost(navHost, startDestination = NavigationQueue.Sscreen.route) {
        composable(route = NavigationQueue.Sscreen.route) {
            SplashScreens(navHost)
        }
        composable(route = NavigationQueue.MainScreen.route) {
            MainDisplay()
        }
    }
}