package com.example.smartzonetestcompose.network.api

import com.example.smartzonetestcompose.network.model.NewsInfo
import com.example.smartzonetestcompose.network.util.NetworkUtil
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

open class BaseArticleApi @Inject constructor(baseUrl: String) : NewsApiService {

    private var apiService: NewsApiService = NetworkUtil.getRetrofit(
        baseUrl
    ).create(NewsApiService::class.java)

    override  fun callBackNewsData(
        q: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Single<NewsInfo> {
        return apiService.callBackNewsData(q, from, to, sortBy, apiKey)
    }
}