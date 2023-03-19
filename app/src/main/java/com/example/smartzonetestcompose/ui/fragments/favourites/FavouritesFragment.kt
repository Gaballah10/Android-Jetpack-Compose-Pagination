package com.example.smartzonetestcompose.ui.fragments.favourites

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.ui.fragments.favourites.viewmodel.FavouritesVM
import com.example.smartzonetestcompose.ui.fragments.news.NewsAction
import com.example.smartzonetestcompose.ui.fragments.news.ShowLoadingProgress

@Composable
fun FavouritesFragment() {

    val viewModel: FavouritesVM = hiltViewModel()
    val liveFavouriteAction by viewModel.liveFavouriteDataAction.observeAsState()
    val context = LocalContext.current
    viewModel.getLocalData(context)

    var showFavProgress by remember() {
        mutableStateOf(false)
    }

    when (liveFavouriteAction) {
        FavLocalAction.Loading -> {
            showFavProgress = true
            ShowFavLoadingProgress(showFavProgress)
        }
        is FavLocalAction.Result -> {
            val response = (liveFavouriteAction as FavLocalAction.Result).list
            showFavProgress = false
            showLocalData(context, response, viewModel)
        }
    }
}

@Composable
fun ShowFavLoadingProgress(showProgress: Boolean) {
    AnimatedVisibility(visible = showProgress) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun showLocalData(context: Context, response: List<Article>, viewModel: FavouritesVM) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(items = response) {
                FavouriteItemRow(modifier = Modifier, article = it) { Article ->
                    viewModel.removeArticleFromLocalDatabase(context, data = Article)
                }
            }
        }
    }
}
