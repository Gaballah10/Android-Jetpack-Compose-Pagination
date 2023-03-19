package com.example.smartzonetestcompose.ui.fragments.favourites

import com.example.smartzonetestcompose.network.model.Article


sealed class FavLocalAction {

    object Loading : FavLocalAction()
    class Result(val list: List<Article>) : FavLocalAction()
    class Error(val exception: Throwable) : FavLocalAction()
}