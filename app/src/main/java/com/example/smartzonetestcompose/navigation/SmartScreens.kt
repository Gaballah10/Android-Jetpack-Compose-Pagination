package com.example.smartzonetestcompose.navigation

import java.lang.IllegalArgumentException

enum class SmartScreens {
     SplashScreen,
    HomeScreen,
    DetailsScreen,
    SearchScreen;

    companion object {
         fun fromRoute(route: String?): SmartScreens
          = when(route?.substringBefore("/")) {
              SplashScreen.name -> SplashScreen
             HomeScreen.name -> HomeScreen
             SearchScreen.name -> SearchScreen
             DetailsScreen.name -> DetailsScreen
             null -> HomeScreen
             else -> throw IllegalArgumentException("Route $route is not recognized")
          }
    }


}