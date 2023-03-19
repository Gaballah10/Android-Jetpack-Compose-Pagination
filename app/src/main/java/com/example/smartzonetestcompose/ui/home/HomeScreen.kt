package com.example.smartzonetestcompose.ui.home

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.google.accompanist.insets.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartzonetestcompose.R
import com.example.smartzonetestcompose.ui.fragments.favourites.FavouritesFragment
import com.example.smartzonetestcompose.ui.fragments.news.NewsFragment
import com.example.smartzonetestcompose.ui.fragments.news.viewmodel.NewsVm

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(navController: NavController, newsTitle: String, newsVm: NewsVm = hiltViewModel()) {
    HomeScreenContent(navController, newsTitle, newsVm)
}


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeScreenContent(navController: NavController, newsTitle: String, newsVm: NewsVm) {

    val (selectedTab, setSelectedTab) = remember { mutableStateOf(SmartHomeTabs.News) }
    val tabs = SmartHomeTabs.values()
    Scaffold(
        backgroundColor = colorResource(id = R.color.white),
        bottomBar = {
            BottomNavigation(backgroundColor = colorResource(id = R.color.grey)) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(id = tab.title)) },
                        selected = tab == selectedTab,
                        onClick = { setSelectedTab(tab) },
                        alwaysShowLabel = false,
                        selectedContentColor = colorResource(id = R.color.brown_design),
                        unselectedContentColor = Color(0xFFFFFFFF),
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) {
        when (selectedTab) {
            SmartHomeTabs.News -> NewsFragment(navController, newsTitle, newsVm)
            SmartHomeTabs.Favourites -> FavouritesFragment()
        }
    }
}


enum class SmartHomeTabs(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    News(R.string.home_tab, Icons.Filled.BlurOn),
    Favourites(R.string.fav_tab, Icons.Filled.BookmarkBorder)
}
