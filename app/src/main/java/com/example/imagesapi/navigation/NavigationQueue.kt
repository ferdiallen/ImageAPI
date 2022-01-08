package com.example.imagesapi.navigation

sealed class NavigationQueue(val route: String) {
    object Sscreen:NavigationQueue("splash_screen")
    object MainScreen:NavigationQueue("main_screen")
    object DetailScreen:NavigationQueue("detail_screen")
}
