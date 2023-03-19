package com.example.smartzonetestcompose.network

import com.example.smartzonetestcompose.BuildConfig
import com.example.smartzonetestcompose.network.api.BaseArticleApi
import com.example.smartzonetestcompose.network.api.NewsApiService

object NewsNetwork {
    fun getRadioApi(baseUrl: String = BuildConfig.BASE_URL): NewsApiService {
        return BaseArticleApi(
            baseUrl = baseUrl
        )
    }
}