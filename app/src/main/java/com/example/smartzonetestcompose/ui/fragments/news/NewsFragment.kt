package com.example.smartzonetestcompose.ui.fragments.news

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartzonetestcompose.BuildConfig
import com.example.smartzonetestcompose.R
import com.example.smartzonetestcompose.components.MainAppBarContent
import com.example.smartzonetestcompose.components.showToast
import com.example.smartzonetestcompose.navigation.SmartScreens
import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.network.model.Source
import com.example.smartzonetestcompose.ui.fragments.news.viewmodel.NewsVm
import com.example.smartzonetestcompose.ui.main.viewmodel.MainVM
import com.example.smartzonetestcompose.useCases.NetworkConnectionUseCase
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsFragment(
    navController: NavController,
    newsTitle: String,
    viewModel: NewsVm
) {
    val context = LocalContext.current
    val mainVM: MainVM = hiltViewModel()
    val liveAction by viewModel.liveDataAction.observeAsState()
    val articlesList = viewModel.articleMutableList.collectAsState().value
    val networkConnectionState by mainVM.networkStateLiveData.observeAsState()
    val listState = rememberLazyListState()

    when (liveAction) {
        NewsAction.Loading -> {
            ShowLoadingProgress(viewModel.isMainLoading.value!!)
        }

        is NewsAction.FooterLoading -> {
            showListOfArticles(
                articlesList,
                viewModel,
                listState,
                context,
                navController,
                viewModel.isFooterLoading.value
            )
        }

        is NewsAction.Result -> {
            if (newsTitle == "empty") {
                showListOfArticles(
                    articlesList,
                    viewModel,
                    listState,
                    context,
                    navController,
                    viewModel.isFooterLoading.value
                )
            } else {
                //--- Filter Data ...
                val searchedData = articlesList.filter {
                    it.title.contains(newsTitle)
                }
                viewModel.isFooterLoading.value = false
                viewModel.livedataLoadNextPage.value = false
                showListOfArticles(
                    searchedData,
                    viewModel,
                    listState,
                    context,
                    navController,
                    viewModel.isFooterLoading.value
                )
            }
        }
        is NewsAction.Error -> {
            if (articlesList.isNotEmpty()) {
                showListOfArticles(
                    articlesList,
                    viewModel,
                    listState,
                    context,
                    navController,
                    viewModel.isFooterLoading.value
                )
            }
            if (networkConnectionState == NetworkConnectionUseCase.NetworkStates.NoNetwork) {
                showToast(context, stringResource(R.string.network_messege_alert))
            }
        }
        is NewsAction.DoNothing -> {
            showListOfArticles(
                articlesList,
                viewModel,
                listState,
                context,
                navController,
                false
            )
        }
        else -> {}
    }
}

@Composable
fun showListOfArticles(
    articlesList: List<Article>,
    viewModel: NewsVm,
    listState: LazyListState,
    context: Context,
    navController: NavController,
    showFooter: Boolean?
) {
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            MainAppBarContent("Home", navController)

            LazyColumn(
                state = listState
            ) {
                items(articlesList.size) { index ->
                    val data = articlesList[index]
                    viewModel.isFooterLoading.value = false
                    NewsRow(modifier = Modifier, { Article ->

                        val imageURL =
                            URLEncoder.encode(Article.urlToImage, StandardCharsets.UTF_8.toString())
                        val details = SmartScreens.DetailsScreen.name
                        navController.navigate("$details/${Article.title}/$imageURL/${Article.publishedAt}/${Article.content}")

                    }, article = data) { Article ->
                        viewModel.insertArticle(context, article = Article)
                    }
                    if (index >= articlesList.size - 1 &&
                        viewModel.livedataLoadNextPage.value == true
                    ) {
                        SideEffect {
                            viewModel.isFooterLoading.value = true
                            viewModel.livedataLoadNextPage.value = false
                            viewModel.loadMore(BuildConfig.NEWS_API_KEY)
                        }
                    }
                }
            }
        }
        if (showFooter!!) {
            Box(modifier = Modifier.padding(bottom = 60.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), elevation = 0.dp
                ) {
                    ConstraintLayout {
                        val (progressFooter) = createRefs()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(
                                progressFooter
                            ) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }
                }
            }
        } else {
            Box {}
        }
    }
}

@Composable
fun ShowLoadingProgress(showMainProgress: Boolean) {
    AnimatedVisibility(visible = showMainProgress) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}

