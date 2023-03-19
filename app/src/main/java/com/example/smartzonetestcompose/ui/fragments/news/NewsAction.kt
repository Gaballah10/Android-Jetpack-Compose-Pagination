package com.example.smartzonetestcompose.ui.fragments.news

sealed class NewsAction {

    object Loading : NewsAction()
    object FooterLoading : NewsAction()
    object Result : NewsAction()
    object DoNothing : NewsAction()
    class Error(val exception: Throwable) : NewsAction()

}